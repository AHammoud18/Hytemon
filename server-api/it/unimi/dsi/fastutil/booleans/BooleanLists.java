/*      */ package it.unimi.dsi.fastutil.booleans;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanLists
/*      */ {
/*      */   public static BooleanList shuffle(BooleanList l, Random random) {
/*   47 */     for (int i = l.size(); i-- != 0; ) {
/*   48 */       int p = random.nextInt(i + 1);
/*   49 */       boolean t = l.getBoolean(i);
/*   50 */       l.set(i, l.getBoolean(p));
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
/*      */     extends BooleanCollections.EmptyCollection
/*      */     implements BooleanList, RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(int i) {
/*   70 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(boolean k) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(int i) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int index, boolean k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(int index, boolean k) {
/*   90 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(boolean k) {
/*   95 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(boolean k) {
/*  100 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Boolean> c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Boolean> operator) {
/*  111 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BooleanUnaryOperator operator) {
/*  116 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanList c) {
/*  121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, BooleanCollection c) {
/*  126 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, BooleanList c) {
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
/*      */     public void add(int index, Boolean k) {
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
/*      */     public Boolean get(int index) {
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
/*      */     public boolean add(Boolean k) {
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
/*      */     public Boolean set(int index, Boolean k) {
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
/*      */     public Boolean remove(int k) {
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
/*      */     public void sort(BooleanComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(BooleanComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Boolean> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Boolean> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator() {
/*  240 */       return BooleanIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator iterator() {
/*  245 */       return BooleanIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator(int i) {
/*  250 */       if (i == 0) return BooleanIterators.EMPTY_ITERATOR; 
/*  251 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanList subList(int from, int to) {
/*  256 */       if (from == 0 && to == 0) return this; 
/*  257 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, boolean[] a, int offset, int length) {
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
/*      */     public void addElements(int index, boolean[] a, int offset, int length) {
/*  273 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a) {
/*  278 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(boolean[] a) {
/*  283 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a) {
/*  288 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a, int offset, int length) {
/*  293 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int s) {
/*  298 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Boolean> o) {
/*  303 */       if (o == this) return 0; 
/*  304 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  309 */       return BooleanLists.EMPTY_LIST;
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
/*  329 */       return BooleanLists.EMPTY_LIST;
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
/*      */   public static BooleanList emptyList() {
/*  349 */     return EMPTY_LIST;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractBooleanList
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final boolean element;
/*      */ 
/*      */     
/*      */     protected Singleton(boolean element) {
/*  363 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(int i) {
/*  368 */       if (i == 0) return this.element; 
/*  369 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(boolean k) {
/*  374 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(int i) {
/*  379 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(boolean k) {
/*  384 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(boolean k) {
/*  389 */       return (k == this.element) ? 0 : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean[] toBooleanArray() {
/*  395 */       return new boolean[] { this.element };
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator() {
/*  400 */       return BooleanIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator iterator() {
/*  405 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator spliterator() {
/*  410 */       return BooleanSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator(int i) {
/*  415 */       if (i > 1 || i < 0) throw new IndexOutOfBoundsException(); 
/*  416 */       BooleanListIterator l = listIterator();
/*  417 */       if (i == 1) l.nextBoolean(); 
/*  418 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanList subList(int from, int to) {
/*  424 */       ensureIndex(from);
/*  425 */       ensureIndex(to);
/*  426 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  427 */       if (from != 0 || to != 1) return BooleanLists.EMPTY_LIST; 
/*  428 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEach(Consumer<? super Boolean> action) {
/*  434 */       action.accept(Boolean.valueOf(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Boolean> c) {
/*  439 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Boolean> c) {
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
/*      */     public boolean removeIf(Predicate<? super Boolean> filter) {
/*  460 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Boolean> operator) {
/*  466 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BooleanUnaryOperator operator) {
/*  471 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BooleanConsumer action) {
/*  476 */       action.accept(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanList c) {
/*  481 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, BooleanList c) {
/*  486 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, BooleanCollection c) {
/*  491 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/*  496 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(BooleanCollection c) {
/*  501 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(BooleanCollection c) {
/*  506 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Object[] toArray() {
/*  512 */       return new Object[] { Boolean.valueOf(this.element) };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(BooleanComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(BooleanComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Boolean> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Boolean> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void getElements(int from, boolean[] a, int offset, int length) {
/*  537 */       if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  538 */       if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")"); 
/*  539 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
/*      */       
/*  541 */       if (length <= 0)
/*  542 */         return;  a[offset] = this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  547 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a) {
/*  552 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a, int offset, int length) {
/*  557 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(boolean[] a) {
/*  562 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a) {
/*  567 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a, int offset, int length) {
/*  572 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  577 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  582 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  587 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  592 */       return this;
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
/*      */   public static BooleanList singleton(boolean element) {
/*  604 */     return new Singleton(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanList singleton(Object element) {
/*  615 */     return new Singleton(((Boolean)element).booleanValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedList
/*      */     extends BooleanCollections.SynchronizedCollection
/*      */     implements BooleanList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final BooleanList list;
/*      */     
/*      */     protected SynchronizedList(BooleanList l, Object sync) {
/*  625 */       super(l, sync);
/*  626 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedList(BooleanList l) {
/*  630 */       super(l);
/*  631 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(int i) {
/*  636 */       synchronized (this.sync) {
/*  637 */         return this.list.getBoolean(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(int i, boolean k) {
/*  643 */       synchronized (this.sync) {
/*  644 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, boolean k) {
/*  650 */       synchronized (this.sync) {
/*  651 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(int i) {
/*  657 */       synchronized (this.sync) {
/*  658 */         return this.list.removeBoolean(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(boolean k) {
/*  664 */       synchronized (this.sync) {
/*  665 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(boolean k) {
/*  671 */       synchronized (this.sync) {
/*  672 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(BooleanPredicate filter) {
/*  678 */       synchronized (this.sync) {
/*  679 */         return this.list.removeIf(filter);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BooleanUnaryOperator operator) {
/*  685 */       synchronized (this.sync) {
/*  686 */         this.list.replaceAll(operator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Boolean> c) {
/*  692 */       synchronized (this.sync) {
/*  693 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, boolean[] a, int offset, int length) {
/*  699 */       synchronized (this.sync) {
/*  700 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  706 */       synchronized (this.sync) {
/*  707 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a, int offset, int length) {
/*  713 */       synchronized (this.sync) {
/*  714 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a) {
/*  720 */       synchronized (this.sync) {
/*  721 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(boolean[] a) {
/*  727 */       synchronized (this.sync) {
/*  728 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a) {
/*  734 */       synchronized (this.sync) {
/*  735 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a, int offset, int length) {
/*  741 */       synchronized (this.sync) {
/*  742 */         this.list.setElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  748 */       synchronized (this.sync) {
/*  749 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator() {
/*  755 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator iterator() {
/*  760 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator(int i) {
/*  765 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanList subList(int from, int to) {
/*  770 */       synchronized (this.sync) {
/*  771 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  777 */       if (o == this) return true; 
/*  778 */       synchronized (this.sync) {
/*  779 */         return this.collection.equals(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  785 */       synchronized (this.sync) {
/*  786 */         return this.collection.hashCode();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Boolean> o) {
/*  792 */       synchronized (this.sync) {
/*  793 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, BooleanCollection c) {
/*  799 */       synchronized (this.sync) {
/*  800 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, BooleanList l) {
/*  806 */       synchronized (this.sync) {
/*  807 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanList l) {
/*  813 */       synchronized (this.sync) {
/*  814 */         return this.list.addAll(l);
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
/*      */     public Boolean get(int i) {
/*  826 */       synchronized (this.sync) {
/*  827 */         return this.list.get(i);
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
/*      */     public void add(int i, Boolean k) {
/*  839 */       synchronized (this.sync) {
/*  840 */         this.list.add(i, k);
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
/*      */     public Boolean set(int index, Boolean k) {
/*  852 */       synchronized (this.sync) {
/*  853 */         return this.list.set(index, k);
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
/*      */     public Boolean remove(int i) {
/*  865 */       synchronized (this.sync) {
/*  866 */         return this.list.remove(i);
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
/*  878 */       synchronized (this.sync) {
/*  879 */         return this.list.indexOf(o);
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
/*  891 */       synchronized (this.sync) {
/*  892 */         return this.list.lastIndexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(BooleanComparator comparator) {
/*  898 */       synchronized (this.sync) {
/*  899 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(BooleanComparator comparator) {
/*  905 */       synchronized (this.sync) {
/*  906 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Boolean> comparator) {
/*  913 */       synchronized (this.sync) {
/*  914 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Boolean> comparator) {
/*  921 */       synchronized (this.sync) {
/*  922 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream s) throws IOException {
/*  927 */       synchronized (this.sync) {
/*  928 */         s.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SynchronizedRandomAccessList
/*      */     extends SynchronizedList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected SynchronizedRandomAccessList(BooleanList l, Object sync) {
/*  938 */       super(l, sync);
/*      */     }
/*      */     
/*      */     protected SynchronizedRandomAccessList(BooleanList l) {
/*  942 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanList subList(int from, int to) {
/*  947 */       synchronized (this.sync) {
/*  948 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*      */   public static BooleanList synchronize(BooleanList l) {
/*  961 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l);
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
/*      */   public static BooleanList synchronize(BooleanList l, Object sync) {
/*  974 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList
/*      */     extends BooleanCollections.UnmodifiableCollection
/*      */     implements BooleanList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final BooleanList list;
/*      */     
/*      */     protected UnmodifiableList(BooleanList l) {
/*  984 */       super(l);
/*  985 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(int i) {
/*  990 */       return this.list.getBoolean(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(int i, boolean k) {
/*  995 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, boolean k) {
/* 1000 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(int i) {
/* 1005 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(boolean k) {
/* 1010 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(boolean k) {
/* 1015 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Boolean> c) {
/* 1020 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Boolean> operator) {
/* 1026 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, boolean[] a, int offset, int length) {
/* 1031 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/* 1036 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a, int offset, int length) {
/* 1041 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, boolean[] a) {
/* 1046 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(boolean[] a) {
/* 1051 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a) {
/* 1056 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, boolean[] a, int offset, int length) {
/* 1061 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/* 1066 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator() {
/* 1071 */       return BooleanIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator iterator() {
/* 1076 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanListIterator listIterator(int i) {
/* 1081 */       return BooleanIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanList subList(int from, int to) {
/* 1086 */       return new UnmodifiableList(this.list.subList(from, to));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1091 */       if (o == this) return true; 
/* 1092 */       return this.collection.equals(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1097 */       return this.collection.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Boolean> o) {
/* 1102 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, BooleanCollection c) {
/* 1107 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanList l) {
/* 1112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, BooleanList l) {
/* 1117 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean get(int i) {
/* 1128 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int i, Boolean k) {
/* 1139 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean set(int index, Boolean k) {
/* 1150 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean remove(int i) {
/* 1161 */       throw new UnsupportedOperationException();
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
/* 1172 */       return this.list.indexOf(o);
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
/* 1183 */       return this.list.lastIndexOf(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(BooleanComparator comparator) {
/* 1188 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(BooleanComparator comparator) {
/* 1193 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Boolean> comparator) {
/* 1199 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Boolean> comparator) {
/* 1205 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList
/*      */     extends UnmodifiableList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(BooleanList l) {
/* 1214 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanList subList(int from, int to) {
/* 1219 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*      */   public static BooleanList unmodifiable(BooleanList l) {
/* 1231 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class ImmutableListBase
/*      */     extends AbstractBooleanList
/*      */     implements BooleanList
/*      */   {
/*      */     @Deprecated
/*      */     public final void add(int index, boolean k) {
/* 1244 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(boolean k) {
/* 1255 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(Collection<? extends Boolean> c) {
/* 1266 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, Collection<? extends Boolean> c) {
/* 1277 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeBoolean(int index) {
/* 1288 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean rem(boolean k) {
/* 1299 */       throw new UnsupportedOperationException();
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
/* 1310 */       throw new UnsupportedOperationException();
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
/* 1321 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(Predicate<? super Boolean> c) {
/* 1332 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(BooleanPredicate c) {
/* 1343 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void replaceAll(UnaryOperator<Boolean> operator) {
/* 1354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void add(int index, Boolean k) {
/* 1365 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(Boolean k) {
/* 1376 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final Boolean remove(int index) {
/* 1387 */       throw new UnsupportedOperationException();
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
/* 1398 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final Boolean set(int index, Boolean k) {
/* 1409 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(BooleanCollection c) {
/* 1420 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(BooleanList c) {
/* 1431 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, BooleanCollection c) {
/* 1442 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, BooleanList c) {
/* 1453 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeAll(BooleanCollection c) {
/* 1464 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean retainAll(BooleanCollection c) {
/* 1475 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean set(int index, boolean k) {
/* 1486 */       throw new UnsupportedOperationException();
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
/* 1497 */       throw new UnsupportedOperationException();
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
/* 1508 */       throw new UnsupportedOperationException();
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
/* 1519 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void addElements(int index, boolean[] a, int offset, int length) {
/* 1530 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void setElements(int index, boolean[] a, int offset, int length) {
/* 1541 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(BooleanComparator comp) {
/* 1552 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(BooleanComparator comp) {
/* 1563 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(Comparator<? super Boolean> comparator) {
/* 1574 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(Comparator<? super Boolean> comparator) {
/* 1585 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */