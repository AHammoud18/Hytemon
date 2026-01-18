/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongPredicate;
/*      */ import java.util.function.LongUnaryOperator;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.LongStream;
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
/*      */ public final class LongLists
/*      */ {
/*      */   public static LongList shuffle(LongList l, Random random) {
/*   47 */     for (int i = l.size(); i-- != 0; ) {
/*   48 */       int p = random.nextInt(i + 1);
/*   49 */       long t = l.getLong(i);
/*   50 */       l.set(i, l.getLong(p));
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
/*      */     extends LongCollections.EmptyCollection
/*      */     implements LongList, RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getLong(int i) {
/*   70 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(long k) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(int i) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int index, long k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(int index, long k) {
/*   90 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(long k) {
/*   95 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(long k) {
/*  100 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Long> c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Long> operator) {
/*  111 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(LongUnaryOperator operator) {
/*  116 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongList c) {
/*  121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, LongCollection c) {
/*  126 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, LongList c) {
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
/*      */     public void add(int index, Long k) {
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
/*      */     public Long get(int index) {
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
/*      */     public boolean add(Long k) {
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
/*      */     public Long set(int index, Long k) {
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
/*      */     public Long remove(int k) {
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
/*      */     public void sort(LongComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(LongComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Long> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Long> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator() {
/*  240 */       return LongIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator iterator() {
/*  245 */       return LongIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator(int i) {
/*  250 */       if (i == 0) return LongIterators.EMPTY_ITERATOR; 
/*  251 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public LongList subList(int from, int to) {
/*  256 */       if (from == 0 && to == 0) return this; 
/*  257 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, long[] a, int offset, int length) {
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
/*      */     public void addElements(int index, long[] a, int offset, int length) {
/*  273 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, long[] a) {
/*  278 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(long[] a) {
/*  283 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a) {
/*  288 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a, int offset, int length) {
/*  293 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int s) {
/*  298 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Long> o) {
/*  303 */       if (o == this) return 0; 
/*  304 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  309 */       return LongLists.EMPTY_LIST;
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
/*  329 */       return LongLists.EMPTY_LIST;
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
/*      */   public static LongList emptyList() {
/*  349 */     return EMPTY_LIST;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractLongList
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final long element;
/*      */ 
/*      */     
/*      */     protected Singleton(long element) {
/*  363 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(int i) {
/*  368 */       if (i == 0) return this.element; 
/*  369 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(long k) {
/*  374 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(int i) {
/*  379 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/*  384 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(long k) {
/*  389 */       return (k == this.element) ? 0 : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long[] toLongArray() {
/*  395 */       return new long[] { this.element };
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator() {
/*  400 */       return LongIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator iterator() {
/*  405 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/*  410 */       return LongSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator(int i) {
/*  415 */       if (i > 1 || i < 0) throw new IndexOutOfBoundsException(); 
/*  416 */       LongListIterator l = listIterator();
/*  417 */       if (i == 1) l.nextLong(); 
/*  418 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongList subList(int from, int to) {
/*  424 */       ensureIndex(from);
/*  425 */       ensureIndex(to);
/*  426 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  427 */       if (from != 0 || to != 1) return LongLists.EMPTY_LIST; 
/*  428 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEach(Consumer<? super Long> action) {
/*  434 */       action.accept(Long.valueOf(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Long> c) {
/*  439 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Long> c) {
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
/*      */     public boolean removeIf(Predicate<? super Long> filter) {
/*  460 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Long> operator) {
/*  466 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(LongUnaryOperator operator) {
/*  471 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer action) {
/*  476 */       action.accept(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongList c) {
/*  481 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, LongList c) {
/*  486 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, LongCollection c) {
/*  491 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongCollection c) {
/*  496 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(LongCollection c) {
/*  501 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(LongCollection c) {
/*  506 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(LongPredicate filter) {
/*  511 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Object[] toArray() {
/*  517 */       return new Object[] { Long.valueOf(this.element) };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(LongComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(LongComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Long> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Long> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void getElements(int from, long[] a, int offset, int length) {
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
/*      */     public void addElements(int index, long[] a) {
/*  557 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, long[] a, int offset, int length) {
/*  562 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(long[] a) {
/*  567 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a) {
/*  572 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a, int offset, int length) {
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
/*      */   public static LongList singleton(long element) {
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
/*      */   public static LongList singleton(Object element) {
/*  620 */     return new Singleton(((Long)element).longValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedList
/*      */     extends LongCollections.SynchronizedCollection
/*      */     implements LongList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final LongList list;
/*      */     
/*      */     protected SynchronizedList(LongList l, Object sync) {
/*  630 */       super(l, sync);
/*  631 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedList(LongList l) {
/*  635 */       super(l);
/*  636 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(int i) {
/*  641 */       synchronized (this.sync) {
/*  642 */         return this.list.getLong(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(int i, long k) {
/*  648 */       synchronized (this.sync) {
/*  649 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, long k) {
/*  655 */       synchronized (this.sync) {
/*  656 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(int i) {
/*  662 */       synchronized (this.sync) {
/*  663 */         return this.list.removeLong(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(long k) {
/*  669 */       synchronized (this.sync) {
/*  670 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(long k) {
/*  676 */       synchronized (this.sync) {
/*  677 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(LongPredicate filter) {
/*  683 */       synchronized (this.sync) {
/*  684 */         return this.list.removeIf(filter);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(LongUnaryOperator operator) {
/*  690 */       synchronized (this.sync) {
/*  691 */         this.list.replaceAll(operator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Long> c) {
/*  697 */       synchronized (this.sync) {
/*  698 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, long[] a, int offset, int length) {
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
/*      */     public void addElements(int index, long[] a, int offset, int length) {
/*  718 */       synchronized (this.sync) {
/*  719 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, long[] a) {
/*  725 */       synchronized (this.sync) {
/*  726 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(long[] a) {
/*  732 */       synchronized (this.sync) {
/*  733 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a) {
/*  739 */       synchronized (this.sync) {
/*  740 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a, int offset, int length) {
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
/*      */     public LongListIterator listIterator() {
/*  760 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator iterator() {
/*  765 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator(int i) {
/*  770 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongList subList(int from, int to) {
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
/*      */     public int compareTo(List<? extends Long> o) {
/*  797 */       synchronized (this.sync) {
/*  798 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, LongCollection c) {
/*  804 */       synchronized (this.sync) {
/*  805 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, LongList l) {
/*  811 */       synchronized (this.sync) {
/*  812 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongList l) {
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
/*      */     public Long get(int i) {
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
/*      */     public void add(int i, Long k) {
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
/*      */     public Long set(int index, Long k) {
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
/*      */     public Long remove(int i) {
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
/*      */     public void sort(LongComparator comparator) {
/*  903 */       synchronized (this.sync) {
/*  904 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(LongComparator comparator) {
/*  910 */       synchronized (this.sync) {
/*  911 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Long> comparator) {
/*  918 */       synchronized (this.sync) {
/*  919 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Long> comparator) {
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
/*      */     protected SynchronizedRandomAccessList(LongList l, Object sync) {
/*  943 */       super(l, sync);
/*      */     }
/*      */     
/*      */     protected SynchronizedRandomAccessList(LongList l) {
/*  947 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongList subList(int from, int to) {
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
/*      */   public static LongList synchronize(LongList l) {
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
/*      */   public static LongList synchronize(LongList l, Object sync) {
/*  979 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList
/*      */     extends LongCollections.UnmodifiableCollection
/*      */     implements LongList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final LongList list;
/*      */     
/*      */     protected UnmodifiableList(LongList l) {
/*  989 */       super(l);
/*  990 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(int i) {
/*  995 */       return this.list.getLong(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(int i, long k) {
/* 1000 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, long k) {
/* 1005 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(int i) {
/* 1010 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(long k) {
/* 1015 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(long k) {
/* 1020 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Long> c) {
/* 1025 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Long> operator) {
/* 1031 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, long[] a, int offset, int length) {
/* 1036 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/* 1041 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, long[] a, int offset, int length) {
/* 1046 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, long[] a) {
/* 1051 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(long[] a) {
/* 1056 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a) {
/* 1061 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, long[] a, int offset, int length) {
/* 1066 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/* 1071 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator() {
/* 1076 */       return LongIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator iterator() {
/* 1081 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator(int i) {
/* 1086 */       return LongIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public LongList subList(int from, int to) {
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
/*      */     public int compareTo(List<? extends Long> o) {
/* 1107 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, LongCollection c) {
/* 1112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongList l) {
/* 1117 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, LongList l) {
/* 1122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(LongUnaryOperator operator) {
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
/*      */     public Long get(int i) {
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
/*      */     public void add(int i, Long k) {
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
/*      */     public Long set(int index, Long k) {
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
/*      */     public Long remove(int i) {
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
/*      */     public void sort(LongComparator comparator) {
/* 1198 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(LongComparator comparator) {
/* 1203 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Long> comparator) {
/* 1209 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Long> comparator) {
/* 1215 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList
/*      */     extends UnmodifiableList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(LongList l) {
/* 1224 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongList subList(int from, int to) {
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
/*      */   public static LongList unmodifiable(LongList l) {
/* 1241 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class ImmutableListBase
/*      */     extends AbstractLongList
/*      */     implements LongList
/*      */   {
/*      */     @Deprecated
/*      */     public final void add(int index, long k) {
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
/*      */     public final boolean add(long k) {
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
/*      */     public final boolean addAll(Collection<? extends Long> c) {
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
/*      */     public final boolean addAll(int index, Collection<? extends Long> c) {
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
/*      */     public final long removeLong(int index) {
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
/*      */     public final boolean rem(long k) {
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
/*      */     public final boolean removeIf(Predicate<? super Long> c) {
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
/*      */     public final boolean removeIf(LongPredicate c) {
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
/*      */     public final void replaceAll(UnaryOperator<Long> operator) {
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
/*      */     public final void replaceAll(LongUnaryOperator operator) {
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
/*      */     public final void add(int index, Long k) {
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
/*      */     public final boolean add(Long k) {
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
/*      */     public final Long remove(int index) {
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
/*      */     public final Long set(int index, Long k) {
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
/*      */     public final boolean addAll(LongCollection c) {
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
/*      */     public final boolean addAll(LongList c) {
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
/*      */     public final boolean addAll(int index, LongCollection c) {
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
/*      */     public final boolean addAll(int index, LongList c) {
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
/*      */     public final boolean removeAll(LongCollection c) {
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
/*      */     public final boolean retainAll(LongCollection c) {
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
/*      */     public final long set(int index, long k) {
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
/*      */     public final void addElements(int index, long[] a, int offset, int length) {
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
/*      */     public final void setElements(int index, long[] a, int offset, int length) {
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
/*      */     public final void sort(LongComparator comp) {
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
/*      */     public final void unstableSort(LongComparator comp) {
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
/*      */     public final void sort(Comparator<? super Long> comparator) {
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
/*      */     public final void unstableSort(Comparator<? super Long> comparator) {
/* 1606 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */