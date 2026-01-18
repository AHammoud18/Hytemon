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
/*      */ import java.util.Objects;
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
/*      */ public final class ObjectLists
/*      */ {
/*      */   public static <K> ObjectList<K> shuffle(ObjectList<K> l, Random random) {
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
/*      */     extends ObjectCollections.EmptyCollection<K>
/*      */     implements ObjectList<K>, RandomAccess, Serializable, Cloneable
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
/*      */     public ObjectList<K> subList(int from, int to) {
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
/*      */     public int compareTo(List<? extends K> o) {
/*  186 */       if (o == this) return 0; 
/*  187 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  192 */       return ObjectLists.EMPTY_LIST;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  197 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  203 */       return (o instanceof List && ((List)o).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  208 */       return "[]";
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  212 */       return ObjectLists.EMPTY_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  220 */   public static final EmptyList EMPTY_LIST = new EmptyList();
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
/*      */   public static <K> ObjectList<K> emptyList() {
/*  232 */     return EMPTY_LIST;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Singleton<K>
/*      */     extends AbstractObjectList<K>
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final K element;
/*      */ 
/*      */     
/*      */     protected Singleton(K element) {
/*  246 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  251 */       if (i == 0) return this.element; 
/*  252 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/*  257 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*  262 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/*  267 */       return Objects.equals(k, this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*  272 */       return Objects.equals(k, this.element) ? 0 : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object[] toArray() {
/*  278 */       return new Object[] { this.element };
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  283 */       return ObjectIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  288 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/*  293 */       return ObjectSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  298 */       if (i > 1 || i < 0) throw new IndexOutOfBoundsException(); 
/*  299 */       ObjectListIterator<K> l = listIterator();
/*  300 */       if (i == 1) l.next(); 
/*  301 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectList<K> subList(int from, int to) {
/*  307 */       ensureIndex(from);
/*  308 */       ensureIndex(to);
/*  309 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  310 */       if (from != 0 || to != 1) return ObjectLists.EMPTY_LIST; 
/*  311 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> action) {
/*  316 */       action.accept(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends K> c) {
/*  321 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends K> c) {
/*  326 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  331 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  336 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(Predicate<? super K> filter) {
/*  341 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  346 */       throw new UnsupportedOperationException();
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
/*  360 */       if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  361 */       if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")"); 
/*  362 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
/*      */       
/*  364 */       if (length <= 0)
/*  365 */         return;  a[offset] = this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  370 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  375 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  380 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  385 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  390 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  395 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  400 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  405 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  410 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  415 */       return this;
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
/*      */   public static <K> ObjectList<K> singleton(K element) {
/*  427 */     return new Singleton<>(element);
/*      */   }
/*      */   
/*      */   public static class SynchronizedList<K>
/*      */     extends ObjectCollections.SynchronizedCollection<K>
/*      */     implements ObjectList<K>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ObjectList<K> list;
/*      */     
/*      */     protected SynchronizedList(ObjectList<K> l, Object sync) {
/*  437 */       super(l, sync);
/*  438 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedList(ObjectList<K> l) {
/*  442 */       super(l);
/*  443 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  448 */       synchronized (this.sync) {
/*  449 */         return this.list.get(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public K set(int i, K k) {
/*  455 */       synchronized (this.sync) {
/*  456 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, K k) {
/*  462 */       synchronized (this.sync) {
/*  463 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*  469 */       synchronized (this.sync) {
/*  470 */         return this.list.remove(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*  476 */       synchronized (this.sync) {
/*  477 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(Object k) {
/*  483 */       synchronized (this.sync) {
/*  484 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(Predicate<? super K> filter) {
/*  490 */       synchronized (this.sync) {
/*  491 */         return this.list.removeIf(filter);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  497 */       synchronized (this.sync) {
/*  498 */         this.list.replaceAll(operator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends K> c) {
/*  504 */       synchronized (this.sync) {
/*  505 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, Object[] a, int offset, int length) {
/*  511 */       synchronized (this.sync) {
/*  512 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  518 */       synchronized (this.sync) {
/*  519 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  525 */       synchronized (this.sync) {
/*  526 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  532 */       synchronized (this.sync) {
/*  533 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  539 */       synchronized (this.sync) {
/*  540 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  546 */       synchronized (this.sync) {
/*  547 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  553 */       synchronized (this.sync) {
/*  554 */         this.list.setElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  560 */       synchronized (this.sync) {
/*  561 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  567 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  572 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  577 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectList<K> subList(int from, int to) {
/*  582 */       synchronized (this.sync) {
/*  583 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  589 */       if (o == this) return true; 
/*  590 */       synchronized (this.sync) {
/*  591 */         return this.collection.equals(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  597 */       synchronized (this.sync) {
/*  598 */         return this.collection.hashCode();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends K> o) {
/*  604 */       synchronized (this.sync) {
/*  605 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super K> comparator) {
/*  611 */       synchronized (this.sync) {
/*  612 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(Comparator<? super K> comparator) {
/*  618 */       synchronized (this.sync) {
/*  619 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream s) throws IOException {
/*  624 */       synchronized (this.sync) {
/*  625 */         s.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SynchronizedRandomAccessList<K>
/*      */     extends SynchronizedList<K> implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected SynchronizedRandomAccessList(ObjectList<K> l, Object sync) {
/*  635 */       super(l, sync);
/*      */     }
/*      */     
/*      */     protected SynchronizedRandomAccessList(ObjectList<K> l) {
/*  639 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectList<K> subList(int from, int to) {
/*  644 */       synchronized (this.sync) {
/*  645 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*      */   public static <K> ObjectList<K> synchronize(ObjectList<K> l) {
/*  658 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(l) : new SynchronizedList<>(l);
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
/*      */   public static <K> ObjectList<K> synchronize(ObjectList<K> l, Object sync) {
/*  671 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(l, sync) : new SynchronizedList<>(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList<K>
/*      */     extends ObjectCollections.UnmodifiableCollection<K>
/*      */     implements ObjectList<K>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ObjectList<? extends K> list;
/*      */     
/*      */     protected UnmodifiableList(ObjectList<? extends K> l) {
/*  681 */       super(l);
/*  682 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  687 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public K set(int i, K k) {
/*  692 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, K k) {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K remove(int i) {
/*  702 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object k) {
/*  707 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(Object k) {
/*  712 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends K> c) {
/*  717 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<K> operator) {
/*  722 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, Object[] a, int offset, int length) {
/*  727 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  732 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a, int offset, int length) {
/*  737 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, K[] a) {
/*  742 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(K[] a) {
/*  747 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a) {
/*  752 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, K[] a, int offset, int length) {
/*  757 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  762 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator() {
/*  767 */       return ObjectIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/*  772 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int i) {
/*  777 */       return ObjectIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectList<K> subList(int from, int to) {
/*  782 */       return new UnmodifiableList(this.list.subList(from, to));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  787 */       if (o == this) return true; 
/*  788 */       return this.collection.equals(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  793 */       return this.collection.hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends K> o) {
/*  799 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super K> comparator) {
/*  804 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(Comparator<? super K> comparator) {
/*  809 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList<K>
/*      */     extends UnmodifiableList<K> implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(ObjectList<? extends K> l) {
/*  818 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectList<K> subList(int from, int to) {
/*  823 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*      */   public static <K> ObjectList<K> unmodifiable(ObjectList<? extends K> l) {
/*  835 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList<>(l) : new UnmodifiableList<>(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class ImmutableListBase<K>
/*      */     extends AbstractObjectList<K>
/*      */     implements ObjectList<K>
/*      */   {
/*      */     @Deprecated
/*      */     public final void add(int index, K k) {
/*  848 */       throw new UnsupportedOperationException();
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
/*  859 */       throw new UnsupportedOperationException();
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
/*  870 */       throw new UnsupportedOperationException();
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
/*  881 */       throw new UnsupportedOperationException();
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
/*  892 */       throw new UnsupportedOperationException();
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
/*  903 */       throw new UnsupportedOperationException();
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
/*  914 */       throw new UnsupportedOperationException();
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
/*  925 */       throw new UnsupportedOperationException();
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
/*  936 */       throw new UnsupportedOperationException();
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
/*  947 */       throw new UnsupportedOperationException();
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
/*  958 */       throw new UnsupportedOperationException();
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
/*  969 */       throw new UnsupportedOperationException();
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
/*  980 */       throw new UnsupportedOperationException();
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
/*  991 */       throw new UnsupportedOperationException();
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
/* 1002 */       throw new UnsupportedOperationException();
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
/* 1013 */       throw new UnsupportedOperationException();
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
/* 1024 */       throw new UnsupportedOperationException();
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
/* 1035 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */