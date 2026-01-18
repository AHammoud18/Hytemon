/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterators;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
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
/*      */ 
/*      */ 
/*      */ public final class ByteLists
/*      */ {
/*      */   public static ByteList shuffle(ByteList l, Random random) {
/*   51 */     for (int i = l.size(); i-- != 0; ) {
/*   52 */       int p = random.nextInt(i + 1);
/*   53 */       byte t = l.getByte(i);
/*   54 */       l.set(i, l.getByte(p));
/*   55 */       l.set(p, t);
/*      */     } 
/*   57 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EmptyList
/*      */     extends ByteCollections.EmptyCollection
/*      */     implements ByteList, RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte getByte(int i) {
/*   74 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(byte k) {
/*   79 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte removeByte(int i) {
/*   84 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int index, byte k) {
/*   89 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte set(int index, byte k) {
/*   94 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(byte k) {
/*   99 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(byte k) {
/*  104 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Byte> c) {
/*  109 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Byte> operator) {
/*  115 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(ByteUnaryOperator operator) {
/*  120 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(ByteList c) {
/*  125 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, ByteCollection c) {
/*  130 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, ByteList c) {
/*  135 */       throw new UnsupportedOperationException();
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
/*      */     public void add(int index, Byte k) {
/*  147 */       throw new UnsupportedOperationException();
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
/*      */     public Byte get(int index) {
/*  159 */       throw new UnsupportedOperationException();
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
/*      */     public boolean add(Byte k) {
/*  171 */       throw new UnsupportedOperationException();
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
/*      */     public Byte set(int index, Byte k) {
/*  183 */       throw new UnsupportedOperationException();
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
/*      */     public Byte remove(int k) {
/*  195 */       throw new UnsupportedOperationException();
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
/*  207 */       return -1;
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
/*  219 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(ByteComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(ByteComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Byte> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Byte> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator() {
/*  244 */       return ByteIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator iterator() {
/*  249 */       return ByteIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator(int i) {
/*  254 */       if (i == 0) return ByteIterators.EMPTY_ITERATOR; 
/*  255 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteList subList(int from, int to) {
/*  260 */       if (from == 0 && to == 0) return this; 
/*  261 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, byte[] a, int offset, int length) {
/*  266 */       if (from == 0 && length == 0 && offset >= 0 && offset <= a.length)
/*  267 */         return;  throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  272 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a, int offset, int length) {
/*  277 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a) {
/*  282 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(byte[] a) {
/*  287 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a) {
/*  292 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a, int offset, int length) {
/*  297 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int s) {
/*  302 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Byte> o) {
/*  307 */       if (o == this) return 0; 
/*  308 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  313 */       return ByteLists.EMPTY_LIST;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  318 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  324 */       return (o instanceof List && ((List)o).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  329 */       return "[]";
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  333 */       return ByteLists.EMPTY_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  341 */   public static final EmptyList EMPTY_LIST = new EmptyList();
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
/*      */   public static ByteList emptyList() {
/*  353 */     return EMPTY_LIST;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractByteList
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final byte element;
/*      */ 
/*      */     
/*      */     protected Singleton(byte element) {
/*  367 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(int i) {
/*  372 */       if (i == 0) return this.element; 
/*  373 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(byte k) {
/*  378 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte removeByte(int i) {
/*  383 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(byte k) {
/*  388 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(byte k) {
/*  393 */       return (k == this.element) ? 0 : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] toByteArray() {
/*  399 */       return new byte[] { this.element };
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator() {
/*  404 */       return ByteIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator iterator() {
/*  409 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator spliterator() {
/*  414 */       return ByteSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator(int i) {
/*  419 */       if (i > 1 || i < 0) throw new IndexOutOfBoundsException(); 
/*  420 */       ByteListIterator l = listIterator();
/*  421 */       if (i == 1) l.nextByte(); 
/*  422 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteList subList(int from, int to) {
/*  428 */       ensureIndex(from);
/*  429 */       ensureIndex(to);
/*  430 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  431 */       if (from != 0 || to != 1) return ByteLists.EMPTY_LIST; 
/*  432 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEach(Consumer<? super Byte> action) {
/*  438 */       action.accept(Byte.valueOf(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Byte> c) {
/*  443 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Byte> c) {
/*  448 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  453 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  458 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean removeIf(Predicate<? super Byte> filter) {
/*  464 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Byte> operator) {
/*  470 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(ByteUnaryOperator operator) {
/*  475 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(ByteConsumer action) {
/*  480 */       action.accept(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(ByteList c) {
/*  485 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, ByteList c) {
/*  490 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int i, ByteCollection c) {
/*  495 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(ByteCollection c) {
/*  500 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(ByteCollection c) {
/*  505 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(ByteCollection c) {
/*  510 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(BytePredicate filter) {
/*  515 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntIterator intIterator() {
/*  520 */       return (IntIterator)IntIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator intSpliterator() {
/*  525 */       return IntSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Object[] toArray() {
/*  531 */       return new Object[] { Byte.valueOf(this.element) };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(ByteComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(ByteComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Byte> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Byte> comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void getElements(int from, byte[] a, int offset, int length) {
/*  556 */       if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  557 */       if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")"); 
/*  558 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
/*      */       
/*  560 */       if (length <= 0)
/*  561 */         return;  a[offset] = this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  566 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a) {
/*  571 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a, int offset, int length) {
/*  576 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(byte[] a) {
/*  581 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a) {
/*  586 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a, int offset, int length) {
/*  591 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  596 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  601 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  606 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  611 */       return this;
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
/*      */   public static ByteList singleton(byte element) {
/*  623 */     return new Singleton(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteList singleton(Object element) {
/*  634 */     return new Singleton(((Byte)element).byteValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedList
/*      */     extends ByteCollections.SynchronizedCollection
/*      */     implements ByteList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ByteList list;
/*      */     
/*      */     protected SynchronizedList(ByteList l, Object sync) {
/*  644 */       super(l, sync);
/*  645 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedList(ByteList l) {
/*  649 */       super(l);
/*  650 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(int i) {
/*  655 */       synchronized (this.sync) {
/*  656 */         return this.list.getByte(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public byte set(int i, byte k) {
/*  662 */       synchronized (this.sync) {
/*  663 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, byte k) {
/*  669 */       synchronized (this.sync) {
/*  670 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public byte removeByte(int i) {
/*  676 */       synchronized (this.sync) {
/*  677 */         return this.list.removeByte(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(byte k) {
/*  683 */       synchronized (this.sync) {
/*  684 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(byte k) {
/*  690 */       synchronized (this.sync) {
/*  691 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeIf(BytePredicate filter) {
/*  697 */       synchronized (this.sync) {
/*  698 */         return this.list.removeIf(filter);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(ByteUnaryOperator operator) {
/*  704 */       synchronized (this.sync) {
/*  705 */         this.list.replaceAll(operator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Byte> c) {
/*  711 */       synchronized (this.sync) {
/*  712 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, byte[] a, int offset, int length) {
/*  718 */       synchronized (this.sync) {
/*  719 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  725 */       synchronized (this.sync) {
/*  726 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a, int offset, int length) {
/*  732 */       synchronized (this.sync) {
/*  733 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a) {
/*  739 */       synchronized (this.sync) {
/*  740 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(byte[] a) {
/*  746 */       synchronized (this.sync) {
/*  747 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a) {
/*  753 */       synchronized (this.sync) {
/*  754 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a, int offset, int length) {
/*  760 */       synchronized (this.sync) {
/*  761 */         this.list.setElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/*  767 */       synchronized (this.sync) {
/*  768 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator() {
/*  774 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator iterator() {
/*  779 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator(int i) {
/*  784 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteList subList(int from, int to) {
/*  789 */       synchronized (this.sync) {
/*  790 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  796 */       if (o == this) return true; 
/*  797 */       synchronized (this.sync) {
/*  798 */         return this.collection.equals(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  804 */       synchronized (this.sync) {
/*  805 */         return this.collection.hashCode();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Byte> o) {
/*  811 */       synchronized (this.sync) {
/*  812 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, ByteCollection c) {
/*  818 */       synchronized (this.sync) {
/*  819 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, ByteList l) {
/*  825 */       synchronized (this.sync) {
/*  826 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(ByteList l) {
/*  832 */       synchronized (this.sync) {
/*  833 */         return this.list.addAll(l);
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
/*      */     public Byte get(int i) {
/*  845 */       synchronized (this.sync) {
/*  846 */         return this.list.get(i);
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
/*      */     public void add(int i, Byte k) {
/*  858 */       synchronized (this.sync) {
/*  859 */         this.list.add(i, k);
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
/*      */     public Byte set(int index, Byte k) {
/*  871 */       synchronized (this.sync) {
/*  872 */         return this.list.set(index, k);
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
/*      */     public Byte remove(int i) {
/*  884 */       synchronized (this.sync) {
/*  885 */         return this.list.remove(i);
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
/*  897 */       synchronized (this.sync) {
/*  898 */         return this.list.indexOf(o);
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
/*  910 */       synchronized (this.sync) {
/*  911 */         return this.list.lastIndexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(ByteComparator comparator) {
/*  917 */       synchronized (this.sync) {
/*  918 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(ByteComparator comparator) {
/*  924 */       synchronized (this.sync) {
/*  925 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Byte> comparator) {
/*  932 */       synchronized (this.sync) {
/*  933 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Byte> comparator) {
/*  940 */       synchronized (this.sync) {
/*  941 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream s) throws IOException {
/*  946 */       synchronized (this.sync) {
/*  947 */         s.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SynchronizedRandomAccessList
/*      */     extends SynchronizedList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected SynchronizedRandomAccessList(ByteList l, Object sync) {
/*  957 */       super(l, sync);
/*      */     }
/*      */     
/*      */     protected SynchronizedRandomAccessList(ByteList l) {
/*  961 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteList subList(int from, int to) {
/*  966 */       synchronized (this.sync) {
/*  967 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*      */   public static ByteList synchronize(ByteList l) {
/*  980 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l);
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
/*      */   public static ByteList synchronize(ByteList l, Object sync) {
/*  993 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList
/*      */     extends ByteCollections.UnmodifiableCollection
/*      */     implements ByteList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ByteList list;
/*      */     
/*      */     protected UnmodifiableList(ByteList l) {
/* 1003 */       super(l);
/* 1004 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(int i) {
/* 1009 */       return this.list.getByte(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte set(int i, byte k) {
/* 1014 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int i, byte k) {
/* 1019 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte removeByte(int i) {
/* 1024 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(byte k) {
/* 1029 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(byte k) {
/* 1034 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Byte> c) {
/* 1039 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void replaceAll(UnaryOperator<Byte> operator) {
/* 1045 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(int from, byte[] a, int offset, int length) {
/* 1050 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/* 1055 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a, int offset, int length) {
/* 1060 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(int index, byte[] a) {
/* 1065 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(byte[] a) {
/* 1070 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a) {
/* 1075 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setElements(int index, byte[] a, int offset, int length) {
/* 1080 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(int size) {
/* 1085 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator() {
/* 1090 */       return ByteIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator iterator() {
/* 1095 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator listIterator(int i) {
/* 1100 */       return ByteIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteList subList(int from, int to) {
/* 1105 */       return new UnmodifiableList(this.list.subList(from, to));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1110 */       if (o == this) return true; 
/* 1111 */       return this.collection.equals(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1116 */       return this.collection.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Byte> o) {
/* 1121 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, ByteCollection c) {
/* 1126 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(ByteList l) {
/* 1131 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, ByteList l) {
/* 1136 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(IntUnaryOperator operator) {
/* 1141 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte get(int i) {
/* 1152 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int i, Byte k) {
/* 1163 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte set(int index, Byte k) {
/* 1174 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte remove(int i) {
/* 1185 */       throw new UnsupportedOperationException();
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
/* 1196 */       return this.list.indexOf(o);
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
/* 1207 */       return this.list.lastIndexOf(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(ByteComparator comparator) {
/* 1212 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void unstableSort(ByteComparator comparator) {
/* 1217 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Byte> comparator) {
/* 1223 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Byte> comparator) {
/* 1229 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList
/*      */     extends UnmodifiableList implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(ByteList l) {
/* 1238 */       super(l);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteList subList(int from, int to) {
/* 1243 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*      */   public static ByteList unmodifiable(ByteList l) {
/* 1255 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class ImmutableListBase
/*      */     extends AbstractByteList
/*      */     implements ByteList
/*      */   {
/*      */     @Deprecated
/*      */     public final void add(int index, byte k) {
/* 1268 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(byte k) {
/* 1279 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(Collection<? extends Byte> c) {
/* 1290 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, Collection<? extends Byte> c) {
/* 1301 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final byte removeByte(int index) {
/* 1312 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean rem(byte k) {
/* 1323 */       throw new UnsupportedOperationException();
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
/* 1334 */       throw new UnsupportedOperationException();
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
/* 1345 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(Predicate<? super Byte> c) {
/* 1356 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeIf(BytePredicate c) {
/* 1367 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void replaceAll(UnaryOperator<Byte> operator) {
/* 1378 */       throw new UnsupportedOperationException();
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
/* 1389 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void add(int index, Byte k) {
/* 1400 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean add(Byte k) {
/* 1411 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final Byte remove(int index) {
/* 1422 */       throw new UnsupportedOperationException();
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
/* 1433 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final Byte set(int index, Byte k) {
/* 1444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(ByteCollection c) {
/* 1455 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(ByteList c) {
/* 1466 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, ByteCollection c) {
/* 1477 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean addAll(int index, ByteList c) {
/* 1488 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean removeAll(ByteCollection c) {
/* 1499 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final boolean retainAll(ByteCollection c) {
/* 1510 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final byte set(int index, byte k) {
/* 1521 */       throw new UnsupportedOperationException();
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
/* 1532 */       throw new UnsupportedOperationException();
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
/* 1543 */       throw new UnsupportedOperationException();
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
/* 1554 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void addElements(int index, byte[] a, int offset, int length) {
/* 1565 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void setElements(int index, byte[] a, int offset, int length) {
/* 1576 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(ByteComparator comp) {
/* 1587 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(ByteComparator comp) {
/* 1598 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void sort(Comparator<? super Byte> comparator) {
/* 1609 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public final void unstableSort(Comparator<? super Byte> comparator) {
/* 1620 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */