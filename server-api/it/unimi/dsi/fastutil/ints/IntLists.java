/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntPredicate;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.IntStream;
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
/*      */ public final class IntLists
/*      */ {
/*      */   public static IntList shuffle(IntList l, Random random) {
/*   47 */     for (int i = l.size(); i-- != 0; ) {
/*   48 */       int p = random.nextInt(i + 1);
/*   49 */       int t = l.getInt(i);
/*   50 */       l.set(i, l.getInt(p));
/*   51 */       l.set(p, t);
/*      */     } 
/*   53 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EmptyList
/*      */     extends IntCollections.EmptyCollection
/*      */     implements IntList, RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(int i) {
/*   70 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(int k) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(int i) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int index, int k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(int index, int k) {
/*   90 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(int k) {
/*   95 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(int k) {
/*  100 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Integer> c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Integer> operator) {
/*  111 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(IntUnaryOperator operator) {
/*  116 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntList c) {
/*  121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, IntCollection c) {
/*  126 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, IntList c) {
/*  131 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int index, Integer k) {
/*  143 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer get(int index) {
/*  155 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Integer k) {
/*  167 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer set(int index, Integer k) {
/*  179 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer remove(int k) {
/*  191 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int indexOf(Object k) {
/*  203 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int lastIndexOf(Object k) {
/*  215 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(IntComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(IntComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Integer> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Integer> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator() {
/*  240 */       return IntIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator iterator() {
/*  245 */       return IntIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator(int i) {
/*  250 */       if (i == 0) return IntIterators.EMPTY_ITERATOR; 
/*  251 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public IntList subList(int from, int to) {
/*  256 */       if (from == 0 && to == 0) return this; 
/*  257 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, int[] a, int offset, int length) {
/*  262 */       if (from == 0 && length == 0 && offset >= 0 && offset <= a.length)
/*  263 */         return;  throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  268 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a, int offset, int length) {
/*  273 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a) {
/*  278 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int[] a) {
/*  283 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a) {
/*  288 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a, int offset, int length) {
/*  293 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int s) {
/*  298 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Integer> o) {
/*  303 */       if (o == this) return 0; 
/*  304 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  309 */       return IntLists.EMPTY_LIST;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  314 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  320 */       return (o instanceof List && ((List)o).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  325 */       return "[]";
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  329 */       return IntLists.EMPTY_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  337 */   public static final EmptyList EMPTY_LIST = new EmptyList();
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
/*      */   public static IntList emptyList() {
/*  349 */     return EMPTY_LIST;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractIntList
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final int element;
/*      */ 
/*      */     
/*      */     protected Singleton(int element) {
/*  363 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(int i) {
/*  368 */       if (i == 0) return this.element; 
/*  369 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(int k) {
/*  374 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(int i) {
/*  379 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int k) {
/*  384 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(int k) {
/*  389 */       return (k == this.element) ? 0 : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int[] toIntArray() {
/*  395 */       return new int[] { this.element };
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator() {
/*  400 */       return IntIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator iterator() {
/*  405 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/*  410 */       return IntSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator(int i) {
/*  415 */       if (i > 1 || i < 0) throw new IndexOutOfBoundsException(); 
/*  416 */       IntListIterator l = listIterator();
/*  417 */       if (i == 1) l.nextInt(); 
/*  418 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntList subList(int from, int to) {
/*  424 */       ensureIndex(from);
/*  425 */       ensureIndex(to);
/*  426 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  427 */       if (from != 0 || to != 1) return IntLists.EMPTY_LIST; 
/*  428 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEach(Consumer<? super Integer> action) {
/*  434 */       action.accept(Integer.valueOf(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Integer> c) {
/*  439 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Integer> c) {
/*  444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  449 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean removeIf(Predicate<? super Integer> filter) {
/*  460 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Integer> operator) {
/*  466 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(IntUnaryOperator operator) {
/*  471 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer action) {
/*  476 */       action.accept(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntList c) {
/*  481 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, IntList c) {
/*  486 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, IntCollection c) {
/*  491 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/*  496 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(IntCollection c) {
/*  501 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(IntCollection c) {
/*  506 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(IntPredicate filter) {
/*  511 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Object[] toArray() {
/*  517 */       return new Object[] { Integer.valueOf(this.element) };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(IntComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(IntComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Integer> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Integer> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void getElements(int from, int[] a, int offset, int length) {
/*  542 */       if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  543 */       if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")"); 
/*  544 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
/*      */       
/*  546 */       if (length <= 0)
/*  547 */         return;  a[offset] = this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  552 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a) {
/*  557 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a, int offset, int length) {
/*  562 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int[] a) {
/*  567 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a) {
/*  572 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a, int offset, int length) {
/*  577 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  582 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  587 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  592 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  597 */       return this;
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
/*      */   public static IntList singleton(int element) {
/*  609 */     return new Singleton(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntList singleton(Object element) {
/*  620 */     return new Singleton(((Integer)element).intValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedList
/*      */     extends IntCollections.SynchronizedCollection
/*      */     implements IntList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final IntList list;
/*      */     
/*      */     protected SynchronizedList(IntList l, Object sync) {
/*  630 */       super(l, sync);
/*  631 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedList(IntList l) {
/*  635 */       super(l);
/*  636 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(int i) {
/*  641 */       synchronized (this.sync) {
/*  642 */         return this.list.getInt(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(int i, int k) {
/*  648 */       synchronized (this.sync) {
/*  649 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, int k) {
/*  655 */       synchronized (this.sync) {
/*  656 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(int i) {
/*  662 */       synchronized (this.sync) {
/*  663 */         return this.list.removeInt(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(int k) {
/*  669 */       synchronized (this.sync) {
/*  670 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(int k) {
/*  676 */       synchronized (this.sync) {
/*  677 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(IntPredicate filter) {
/*  683 */       synchronized (this.sync) {
/*  684 */         return this.list.removeIf(filter);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(IntUnaryOperator operator) {
/*  690 */       synchronized (this.sync) {
/*  691 */         this.list.replaceAll(operator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Integer> c) {
/*  697 */       synchronized (this.sync) {
/*  698 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, int[] a, int offset, int length) {
/*  704 */       synchronized (this.sync) {
/*  705 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  711 */       synchronized (this.sync) {
/*  712 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a, int offset, int length) {
/*  718 */       synchronized (this.sync) {
/*  719 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a) {
/*  725 */       synchronized (this.sync) {
/*  726 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int[] a) {
/*  732 */       synchronized (this.sync) {
/*  733 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a) {
/*  739 */       synchronized (this.sync) {
/*  740 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a, int offset, int length) {
/*  746 */       synchronized (this.sync) {
/*  747 */         this.list.setElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  753 */       synchronized (this.sync) {
/*  754 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator() {
/*  760 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator iterator() {
/*  765 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator(int i) {
/*  770 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntList subList(int from, int to) {
/*  775 */       synchronized (this.sync) {
/*  776 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  782 */       if (o == this) return true; 
/*  783 */       synchronized (this.sync) {
/*  784 */         return this.collection.equals(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  790 */       synchronized (this.sync) {
/*  791 */         return this.collection.hashCode();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Integer> o) {
/*  797 */       synchronized (this.sync) {
/*  798 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, IntCollection c) {
/*  804 */       synchronized (this.sync) {
/*  805 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, IntList l) {
/*  811 */       synchronized (this.sync) {
/*  812 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntList l) {
/*  818 */       synchronized (this.sync) {
/*  819 */         return this.list.addAll(l);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer get(int i) {
/*  831 */       synchronized (this.sync) {
/*  832 */         return this.list.get(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int i, Integer k) {
/*  844 */       synchronized (this.sync) {
/*  845 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer set(int index, Integer k) {
/*  857 */       synchronized (this.sync) {
/*  858 */         return this.list.set(index, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer remove(int i) {
/*  870 */       synchronized (this.sync) {
/*  871 */         return this.list.remove(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int indexOf(Object o) {
/*  883 */       synchronized (this.sync) {
/*  884 */         return this.list.indexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int lastIndexOf(Object o) {
/*  896 */       synchronized (this.sync) {
/*  897 */         return this.list.lastIndexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(IntComparator comparator) {
/*  903 */       synchronized (this.sync) {
/*  904 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(IntComparator comparator) {
/*  910 */       synchronized (this.sync) {
/*  911 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Integer> comparator) {
/*  918 */       synchronized (this.sync) {
/*  919 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Integer> comparator) {
/*  926 */       synchronized (this.sync) {
/*  927 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream s) throws IOException {
/*  932 */       synchronized (this.sync) {
/*  933 */         s.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SynchronizedRandomAccessList
/*      */     extends SynchronizedList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected SynchronizedRandomAccessList(IntList l, Object sync) {
/*  943 */       super(l, sync);
/*      */     }
/*      */     
/*      */     protected SynchronizedRandomAccessList(IntList l) {
/*  947 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntList subList(int from, int to) {
/*  952 */       synchronized (this.sync) {
/*  953 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*      */   public static IntList synchronize(IntList l) {
/*  966 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l);
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
/*      */   public static IntList synchronize(IntList l, Object sync) {
/*  979 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList
/*      */     extends IntCollections.UnmodifiableCollection
/*      */     implements IntList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final IntList list;
/*      */     
/*      */     protected UnmodifiableList(IntList l) {
/*  989 */       super(l);
/*  990 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(int i) {
/*  995 */       return this.list.getInt(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(int i, int k) {
/* 1000 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, int k) {
/* 1005 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(int i) {
/* 1010 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(int k) {
/* 1015 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(int k) {
/* 1020 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Integer> c) {
/* 1025 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Integer> operator) {
/* 1031 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, int[] a, int offset, int length) {
/* 1036 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/* 1041 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a, int offset, int length) {
/* 1046 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, int[] a) {
/* 1051 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int[] a) {
/* 1056 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a) {
/* 1061 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, int[] a, int offset, int length) {
/* 1066 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/* 1071 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator() {
/* 1076 */       return IntIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator iterator() {
/* 1081 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator listIterator(int i) {
/* 1086 */       return IntIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public IntList subList(int from, int to) {
/* 1091 */       return new UnmodifiableList(this.list.subList(from, to));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1096 */       if (o == this) return true; 
/* 1097 */       return this.collection.equals(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1102 */       return this.collection.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Integer> o) {
/* 1107 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, IntCollection c) {
/* 1112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntList l) {
/* 1117 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, IntList l) {
/* 1122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(IntUnaryOperator operator) {
/* 1127 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer get(int i) {
/* 1138 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int i, Integer k) {
/* 1149 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer set(int index, Integer k) {
/* 1160 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer remove(int i) {
/* 1171 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int indexOf(Object o) {
/* 1182 */       return this.list.indexOf(o);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int lastIndexOf(Object o) {
/* 1193 */       return this.list.lastIndexOf(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(IntComparator comparator) {
/* 1198 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(IntComparator comparator) {
/* 1203 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Integer> comparator) {
/* 1209 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Integer> comparator) {
/* 1215 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList
/*      */     extends UnmodifiableList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(IntList l) {
/* 1224 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntList subList(int from, int to) {
/* 1229 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*      */   public static IntList unmodifiable(IntList l) {
/* 1241 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class ImmutableListBase
/*      */     extends AbstractIntList
/*      */     implements IntList
/*      */   {
/*      */     @Deprecated
/*      */     public final void add(int index, int k) {
/* 1254 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(int k) {
/* 1265 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(Collection<? extends Integer> c) {
/* 1276 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, Collection<? extends Integer> c) {
/* 1287 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final int removeInt(int index) {
/* 1298 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean rem(int k) {
/* 1309 */       throw new UnsupportedOperationException();
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
/* 1320 */       throw new UnsupportedOperationException();
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
/* 1331 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(Predicate<? super Integer> c) {
/* 1342 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(IntPredicate c) {
/* 1353 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void replaceAll(UnaryOperator<Integer> operator) {
/* 1364 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void replaceAll(IntUnaryOperator operator) {
/* 1375 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void add(int index, Integer k) {
/* 1386 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(Integer k) {
/* 1397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final Integer remove(int index) {
/* 1408 */       throw new UnsupportedOperationException();
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
/* 1419 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final Integer set(int index, Integer k) {
/* 1430 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(IntCollection c) {
/* 1441 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(IntList c) {
/* 1452 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, IntCollection c) {
/* 1463 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, IntList c) {
/* 1474 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeAll(IntCollection c) {
/* 1485 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean retainAll(IntCollection c) {
/* 1496 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final int set(int index, int k) {
/* 1507 */       throw new UnsupportedOperationException();
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
/* 1518 */       throw new UnsupportedOperationException();
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
/* 1529 */       throw new UnsupportedOperationException();
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
/* 1540 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void addElements(int index, int[] a, int offset, int length) {
/* 1551 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void setElements(int index, int[] a, int offset, int length) {
/* 1562 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(IntComparator comp) {
/* 1573 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(IntComparator comp) {
/* 1584 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(Comparator<? super Integer> comparator) {
/* 1595 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(Comparator<? super Integer> comparator) {
/* 1606 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */