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
/*     */ import java.util.Objects;
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
/*     */ public abstract class AbstractObjectList<K>
/*     */   extends AbstractObjectCollection<K>
/*     */   implements ObjectList<K>, Stack<K>
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
/* 165 */           return AbstractObjectList.this.get(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, K k) {
/* 170 */           AbstractObjectList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, K k) {
/* 175 */           AbstractObjectList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 180 */           AbstractObjectList.this.remove(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 185 */           return AbstractObjectList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator<K> extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
/*     */     final ObjectList<K> l;
/*     */     
/*     */     IndexBasedSpliterator(ObjectList<K> l, int pos) {
/* 194 */       super(pos);
/* 195 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ObjectList<K> l, int pos, int maxPos) {
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
/* 236 */       if (Objects.equals(k, e)) return i.previousIndex(); 
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
/* 247 */       if (Objects.equals(k, e)) return i.nextIndex(); 
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
/*     */   public ObjectList<K> subList(int from, int to) {
/* 261 */     ensureIndex(from);
/* 262 */     ensureIndex(to);
/* 263 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 264 */     return (this instanceof RandomAccess) ? new ObjectRandomAccessSubList<>(this, from, to) : new ObjectSubList<>(this, from, to);
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
/* 415 */       h = 31 * h + ((k == null) ? 0 : k.hashCode());
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
/* 428 */     while (s-- != 0) { if (!Objects.equals(i1.next(), i2.next())) return false;  }
/* 429 */      return true;
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
/*     */   public int compareTo(List<? extends K> l) {
/* 445 */     if (l == this) return 0; 
/* 446 */     if (l instanceof ObjectList) {
/* 447 */       ObjectListIterator<K> objectListIterator1 = listIterator(), objectListIterator2 = ((ObjectList)l).listIterator();
/*     */ 
/*     */       
/* 450 */       while (objectListIterator1.hasNext() && objectListIterator2.hasNext()) {
/* 451 */         K e1 = objectListIterator1.next();
/* 452 */         K e2 = objectListIterator2.next(); int r;
/* 453 */         if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0) return r; 
/*     */       } 
/* 455 */       return objectListIterator2.hasNext() ? -1 : (objectListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 457 */     ListIterator<? extends K> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 459 */     while (i1.hasNext() && i2.hasNext()) {
/* 460 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 462 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(K o) {
/* 467 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public K pop() {
/* 472 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 473 */     return remove(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public K top() {
/* 478 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 479 */     return get(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public K peek(int i) {
/* 484 */     return get(size() - 1 - i);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 489 */     StringBuilder s = new StringBuilder();
/* 490 */     ObjectIterator<K> i = iterator();
/* 491 */     int n = size();
/*     */     
/* 493 */     boolean first = true;
/* 494 */     s.append("[");
/* 495 */     while (n-- != 0) {
/* 496 */       if (first) { first = false; }
/* 497 */       else { s.append(", "); }
/* 498 */        K k = i.next();
/* 499 */       if (this == k) { s.append("(this list)"); continue; }
/* 500 */        s.append(String.valueOf(k));
/*     */     } 
/* 502 */     s.append("]");
/* 503 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ObjectSubList<K>
/*     */     extends AbstractObjectList<K>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectList<K> l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ObjectSubList(ObjectList<K> l, int from, int to) {
/* 517 */       this.l = l;
/* 518 */       this.from = from;
/* 519 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 523 */       assert this.from <= this.l.size();
/* 524 */       assert this.to <= this.l.size();
/* 525 */       assert this.to >= this.from;
/* 526 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K k) {
/* 531 */       this.l.add(this.to, k);
/* 532 */       this.to++;
/* 533 */       assert assertRange();
/* 534 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, K k) {
/* 539 */       ensureIndex(index);
/* 540 */       this.l.add(this.from + index, k);
/* 541 */       this.to++;
/* 542 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 547 */       ensureIndex(index);
/* 548 */       this.to += c.size();
/* 549 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(int index) {
/* 554 */       ensureRestrictedIndex(index);
/* 555 */       return this.l.get(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(int index) {
/* 560 */       ensureRestrictedIndex(index);
/* 561 */       this.to--;
/* 562 */       return this.l.remove(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(int index, K k) {
/* 567 */       ensureRestrictedIndex(index);
/* 568 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 573 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 578 */       ensureIndex(from);
/* 579 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")"); 
/* 580 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 585 */       ensureIndex(from);
/* 586 */       ensureIndex(to);
/* 587 */       this.l.removeElements(this.from + from, this.from + to);
/* 588 */       this.to -= to - from;
/* 589 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 594 */       ensureIndex(index);
/* 595 */       this.l.addElements(this.from + index, a, offset, length);
/* 596 */       this.to += length;
/* 597 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 602 */       ensureIndex(index);
/* 603 */       this.l.setElements(this.from + index, a, offset, length);
/* 604 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ObjectIterators.AbstractIndexBasedListIterator<K>
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 613 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final K get(int i) {
/* 618 */         return AbstractObjectList.ObjectSubList.this.l.get(AbstractObjectList.ObjectSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, K k) {
/* 624 */         AbstractObjectList.ObjectSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, K k) {
/* 629 */         AbstractObjectList.ObjectSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 634 */         AbstractObjectList.ObjectSubList.this.remove(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 639 */         return AbstractObjectList.ObjectSubList.this.to - AbstractObjectList.ObjectSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 644 */         super.add(k);
/* 645 */         assert AbstractObjectList.ObjectSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 650 */         super.remove();
/* 651 */         assert AbstractObjectList.ObjectSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ObjectListIterator<K> {
/*     */       private ObjectListIterator<K> parent;
/*     */       
/*     */       ParentWrappingIter(ObjectListIterator<K> parent) {
/* 659 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 664 */         return this.parent.nextIndex() - AbstractObjectList.ObjectSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 669 */         return this.parent.previousIndex() - AbstractObjectList.ObjectSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 674 */         return (this.parent.nextIndex() < AbstractObjectList.ObjectSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 679 */         return (this.parent.previousIndex() >= AbstractObjectList.ObjectSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public K next() {
/* 684 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 685 */         return this.parent.next();
/*     */       }
/*     */ 
/*     */       
/*     */       public K previous() {
/* 690 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 691 */         return this.parent.previous();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 696 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(K k) {
/* 701 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 706 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public int back(int n) {
/* 711 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 712 */         int currentPos = this.parent.previousIndex();
/* 713 */         int parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 717 */         if (parentNewPos < AbstractObjectList.ObjectSubList.this.from - 1) parentNewPos = AbstractObjectList.ObjectSubList.this.from - 1; 
/* 718 */         int toSkip = parentNewPos - currentPos;
/* 719 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 724 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 725 */         int currentPos = this.parent.nextIndex();
/* 726 */         int parentNewPos = currentPos + n;
/* 727 */         if (parentNewPos > AbstractObjectList.ObjectSubList.this.to) parentNewPos = AbstractObjectList.ObjectSubList.this.to; 
/* 728 */         int toSkip = parentNewPos - currentPos;
/* 729 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int index) {
/* 735 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 740 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 745 */       return (this.l instanceof RandomAccess) ? new AbstractObjectList.IndexBasedSpliterator<>(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 750 */       ensureIndex(from);
/* 751 */       ensureIndex(to);
/* 752 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 755 */       return new ObjectSubList(this, from, to);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ObjectRandomAccessSubList<K> extends ObjectSubList<K> implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ObjectRandomAccessSubList(ObjectList<K> l, int from, int to) {
/* 763 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 768 */       ensureIndex(from);
/* 769 */       ensureIndex(to);
/* 770 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 773 */       return new ObjectRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObjectList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */