/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Random;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.Stream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ReferenceLists
/*      */ {
/*      */   public static <K> ReferenceList<K> shuffle(ReferenceList<K> l, Random random) {
/*   43 */     for (int i = l.size(); i-- != 0; ) {
/*   44 */       int p = random.nextInt(i + 1);
/*   45 */       K t = l.get(i);
/*   46 */       l.set(i, l.get(p));
/*   47 */       l.set(p, t);
/*      */     } 
/*   49 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EmptyList<K>
/*      */     extends ReferenceCollections.EmptyCollection<K>
/*      */     implements ReferenceList<K>, RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*   66 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/*   71 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*   76 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int index, K k) {
/*   81 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K set(int index, K k) {
/*   86 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*   91 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(Object k) {
/*   96 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends K> c) {
/*  101 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  106 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super K> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(Comparator<? super K> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  121 */       return ObjectIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  127 */       return ObjectIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  133 */       if (i == 0) return ObjectIterators.EMPTY_ITERATOR; 
/*  134 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceList<K> subList(int from, int to) {
/*  139 */       if (from == 0 && to == 0) return this; 
/*  140 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, Object[] a, int offset, int length) {
/*  145 */       if (from == 0 && length == 0 && offset >= 0 && offset <= a.length)
/*  146 */         return;  throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  151 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  156 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  161 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  166 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  171 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  176 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int s) {
/*  181 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  186 */       return ReferenceLists.EMPTY_LIST;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  191 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  197 */       return (o instanceof List && ((List)o).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  202 */       return "[]";
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  206 */       return ReferenceLists.EMPTY_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  214 */   public static final EmptyList EMPTY_LIST = new EmptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceList<K> emptyList() {
/*  226 */     return EMPTY_LIST;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Singleton<K>
/*      */     extends AbstractReferenceList<K>
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final K element;
/*      */ 
/*      */     
/*      */     protected Singleton(K element) {
/*  240 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  245 */       if (i == 0) return this.element; 
/*  246 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/*  251 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*  256 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/*  261 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*  266 */       return (k == this.element) ? 0 : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object[] toArray() {
/*  272 */       return new Object[] { this.element };
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  277 */       return ObjectIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  282 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/*  287 */       return ObjectSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  292 */       if (i > 1 || i < 0) throw new IndexOutOfBoundsException(); 
/*  293 */       ObjectListIterator<K> l = listIterator();
/*  294 */       if (i == 1) l.next(); 
/*  295 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ReferenceList<K> subList(int from, int to) {
/*  301 */       ensureIndex(from);
/*  302 */       ensureIndex(to);
/*  303 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  304 */       if (from != 0 || to != 1) return ReferenceLists.EMPTY_LIST; 
/*  305 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> action) {
/*  310 */       action.accept(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends K> c) {
/*  315 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends K> c) {
/*  320 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  325 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  330 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(Predicate<? super K> filter) {
/*  335 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  340 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super K> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(Comparator<? super K> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void getElements(int from, Object[] a, int offset, int length) {
/*  354 */       if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  355 */       if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")"); 
/*  356 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
/*      */       
/*  358 */       if (length <= 0)
/*  359 */         return;  a[offset] = this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  364 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  374 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  379 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  384 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  389 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  394 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  399 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  404 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  409 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceList<K> singleton(K element) {
/*  421 */     return new Singleton<>(element);
/*      */   }
/*      */   
/*      */   public static class SynchronizedList<K>
/*      */     extends ReferenceCollections.SynchronizedCollection<K>
/*      */     implements ReferenceList<K>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ReferenceList<K> list;
/*      */     
/*      */     protected SynchronizedList(ReferenceList<K> l, Object sync) {
/*  431 */       super(l, sync);
/*  432 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedList(ReferenceList<K> l) {
/*  436 */       super(l);
/*  437 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  442 */       synchronized (this.sync) {
/*  443 */         return this.list.get(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public K set(int i, K k) {
/*  449 */       synchronized (this.sync) {
/*  450 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, K k) {
/*  456 */       synchronized (this.sync) {
/*  457 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*  463 */       synchronized (this.sync) {
/*  464 */         return this.list.remove(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*  470 */       synchronized (this.sync) {
/*  471 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(Object k) {
/*  477 */       synchronized (this.sync) {
/*  478 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(Predicate<? super K> filter) {
/*  484 */       synchronized (this.sync) {
/*  485 */         return this.list.removeIf(filter);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  491 */       synchronized (this.sync) {
/*  492 */         this.list.replaceAll(operator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends K> c) {
/*  498 */       synchronized (this.sync) {
/*  499 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, Object[] a, int offset, int length) {
/*  505 */       synchronized (this.sync) {
/*  506 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  512 */       synchronized (this.sync) {
/*  513 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  519 */       synchronized (this.sync) {
/*  520 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  526 */       synchronized (this.sync) {
/*  527 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  533 */       synchronized (this.sync) {
/*  534 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  540 */       synchronized (this.sync) {
/*  541 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  547 */       synchronized (this.sync) {
/*  548 */         this.list.setElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  554 */       synchronized (this.sync) {
/*  555 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  561 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  566 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  571 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceList<K> subList(int from, int to) {
/*  576 */       synchronized (this.sync) {
/*  577 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  583 */       if (o == this) return true; 
/*  584 */       synchronized (this.sync) {
/*  585 */         return this.collection.equals(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  591 */       synchronized (this.sync) {
/*  592 */         return this.collection.hashCode();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super K> comparator) {
/*  598 */       synchronized (this.sync) {
/*  599 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(Comparator<? super K> comparator) {
/*  605 */       synchronized (this.sync) {
/*  606 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream s) throws IOException {
/*  611 */       synchronized (this.sync) {
/*  612 */         s.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SynchronizedRandomAccessList<K>
/*      */     extends SynchronizedList<K> implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected SynchronizedRandomAccessList(ReferenceList<K> l, Object sync) {
/*  622 */       super(l, sync);
/*      */     }
/*      */     
/*      */     protected SynchronizedRandomAccessList(ReferenceList<K> l) {
/*  626 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceList<K> subList(int from, int to) {
/*  631 */       synchronized (this.sync) {
/*  632 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceList<K> synchronize(ReferenceList<K> l) {
/*  645 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(l) : new SynchronizedList<>(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceList<K> synchronize(ReferenceList<K> l, Object sync) {
/*  658 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(l, sync) : new SynchronizedList<>(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList<K>
/*      */     extends ReferenceCollections.UnmodifiableCollection<K>
/*      */     implements ReferenceList<K>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ReferenceList<? extends K> list;
/*      */     
/*      */     protected UnmodifiableList(ReferenceList<? extends K> l) {
/*  668 */       super(l);
/*  669 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  674 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public K set(int i, K k) {
/*  679 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, K k) {
/*  684 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*  689 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*  694 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(Object k) {
/*  699 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends K> c) {
/*  704 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  709 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, Object[] a, int offset, int length) {
/*  714 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  719 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  724 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  729 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  734 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  739 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  744 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  749 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  754 */       return ObjectIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  759 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  764 */       return ObjectIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceList<K> subList(int from, int to) {
/*  769 */       return new UnmodifiableList(this.list.subList(from, to));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  774 */       if (o == this) return true; 
/*  775 */       return this.collection.equals(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  780 */       return this.collection.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super K> comparator) {
/*  785 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(Comparator<? super K> comparator) {
/*  790 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList<K>
/*      */     extends UnmodifiableList<K> implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(ReferenceList<? extends K> l) {
/*  799 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceList<K> subList(int from, int to) {
/*  804 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceList<K> unmodifiable(ReferenceList<? extends K> l) {
/*  816 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList<>(l) : new UnmodifiableList<>(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class ImmutableListBase<K>
/*      */     extends AbstractReferenceList<K>
/*      */     implements ReferenceList<K>
/*      */   {
/*      */     @Deprecated
/*      */     public final void add(int index, K k) {
/*  829 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(K k) {
/*  840 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(Collection<? extends K> c) {
/*  851 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, Collection<? extends K> c) {
/*  862 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final K remove(int index) {
/*  873 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean remove(Object k) {
/*  884 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeAll(Collection<?> c) {
/*  895 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean retainAll(Collection<?> c) {
/*  906 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(Predicate<? super K> c) {
/*  917 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void replaceAll(UnaryOperator<K> operator) {
/*  928 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final K set(int index, K k) {
/*  939 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void clear() {
/*  950 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void size(int size) {
/*  961 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void removeElements(int from, int to) {
/*  972 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void addElements(int index, K[] a, int offset, int length) {
/*  983 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void setElements(int index, K[] a, int offset, int length) {
/*  994 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(Comparator<? super K> comparator) {
/* 1005 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(Comparator<? super K> comparator) {
/* 1016 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */