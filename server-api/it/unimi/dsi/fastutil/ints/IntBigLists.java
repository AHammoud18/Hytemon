/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntPredicate;
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
/*      */ public final class IntBigLists
/*      */ {
/*      */   public static IntBigList shuffle(IntBigList l, Random random) {
/*   42 */     for (long i = l.size64(); i-- != 0L; ) {
/*   43 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   44 */       int t = l.getInt(i);
/*   45 */       l.set(i, l.getInt(p));
/*   46 */       l.set(p, t);
/*      */     } 
/*   48 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EmptyBigList
/*      */     extends IntCollections.EmptyCollection
/*      */     implements IntBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(long i) {
/*   65 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(int k) {
/*   70 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(long i) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, int k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(long index, int k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(int k) {
/*   90 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(int k) {
/*   95 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Integer> c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntBigList c) {
/*  110 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, IntCollection c) {
/*  115 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, IntBigList c) {
/*  120 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long index, Integer k) {
/*  131 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Integer k) {
/*  142 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer get(long i) {
/*  153 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer set(long index, Integer k) {
/*  164 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer remove(long k) {
/*  175 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long indexOf(Object k) {
/*  186 */       return -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long lastIndexOf(Object k) {
/*  197 */       return -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  203 */       return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntBigListIterator iterator() {
/*  209 */       return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  215 */       if (i == 0L) return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  216 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/*  222 */       return IntSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  227 */       if (from == 0L && to == 0L) return this; 
/*  228 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, int[][] a, long offset, long length) {
/*  233 */       BigArrays.ensureOffsetLength(a, offset, length);
/*  234 */       if (from != 0L) throw new IndexOutOfBoundsException();
/*      */     
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  239 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, int[][] a, long offset, long length) {
/*  244 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, int[][] a) {
/*  249 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(long s) {
/*  254 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long size64() {
/*  259 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends Integer> o) {
/*  264 */       if (o == this) return 0; 
/*  265 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  270 */       return IntBigLists.EMPTY_BIG_LIST;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  275 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  281 */       return (o instanceof BigList && ((BigList)o).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  286 */       return "[]";
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  290 */       return IntBigLists.EMPTY_BIG_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  298 */   public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractIntBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final int element;
/*      */ 
/*      */     
/*      */     protected Singleton(int element) {
/*  311 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long i) {
/*  316 */       if (i == 0L) return this.element; 
/*  317 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(int k) {
/*  322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(long i) {
/*  327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int k) {
/*  332 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(int k) {
/*  337 */       return (k == this.element) ? 0L : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int[] toIntArray() {
/*  343 */       int[] a = { this.element };
/*  344 */       return a;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  349 */       return IntBigListIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  354 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/*  355 */       IntBigListIterator l = listIterator();
/*  356 */       if (i == 1L) l.nextInt(); 
/*  357 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/*  362 */       return IntSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  368 */       ensureIndex(from);
/*  369 */       ensureIndex(to);
/*  370 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  371 */       if (from != 0L || to != 1L) return IntBigLists.EMPTY_BIG_LIST; 
/*  372 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Integer> c) {
/*  377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Integer> c) {
/*  382 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  387 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  392 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntBigList c) {
/*  397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, IntBigList c) {
/*  402 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, IntCollection c) {
/*  407 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/*  412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(IntCollection c) {
/*  417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(IntCollection c) {
/*  422 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  427 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long size64() {
/*  432 */       return 1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  437 */       return this;
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
/*      */   public static IntBigList singleton(int element) {
/*  449 */     return new Singleton(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigList singleton(Object element) {
/*  460 */     return new Singleton(((Integer)element).intValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends IntCollections.SynchronizedCollection
/*      */     implements IntBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final IntBigList list;
/*      */     
/*      */     protected SynchronizedBigList(IntBigList l, Object sync) {
/*  470 */       super(l, sync);
/*  471 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedBigList(IntBigList l) {
/*  475 */       super(l);
/*  476 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long i) {
/*  481 */       synchronized (this.sync) {
/*  482 */         return this.list.getInt(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(long i, int k) {
/*  488 */       synchronized (this.sync) {
/*  489 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, int k) {
/*  495 */       synchronized (this.sync) {
/*  496 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(long i) {
/*  502 */       synchronized (this.sync) {
/*  503 */         return this.list.removeInt(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(int k) {
/*  509 */       synchronized (this.sync) {
/*  510 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(int k) {
/*  516 */       synchronized (this.sync) {
/*  517 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Integer> c) {
/*  523 */       synchronized (this.sync) {
/*  524 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, int[][] a, long offset, long length) {
/*  530 */       synchronized (this.sync) {
/*  531 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  537 */       synchronized (this.sync) {
/*  538 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, int[][] a, long offset, long length) {
/*  544 */       synchronized (this.sync) {
/*  545 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, int[][] a) {
/*  551 */       synchronized (this.sync) {
/*  552 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void size(long size) {
/*  562 */       synchronized (this.sync) {
/*  563 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long size64() {
/*  569 */       synchronized (this.sync) {
/*  570 */         return this.list.size64();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator iterator() {
/*  576 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  581 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  586 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  591 */       synchronized (this.sync) {
/*  592 */         return IntBigLists.synchronize(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  598 */       if (o == this) return true; 
/*  599 */       synchronized (this.sync) {
/*  600 */         return this.list.equals(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  606 */       synchronized (this.sync) {
/*  607 */         return this.list.hashCode();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends Integer> o) {
/*  613 */       synchronized (this.sync) {
/*  614 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, IntCollection c) {
/*  620 */       synchronized (this.sync) {
/*  621 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, IntBigList l) {
/*  627 */       synchronized (this.sync) {
/*  628 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntBigList l) {
/*  634 */       synchronized (this.sync) {
/*  635 */         return this.list.addAll(l);
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
/*      */     public void add(long i, Integer k) {
/*  647 */       synchronized (this.sync) {
/*  648 */         this.list.add(i, k);
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
/*      */     public Integer get(long i) {
/*  660 */       synchronized (this.sync) {
/*  661 */         return this.list.get(i);
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
/*      */     public Integer set(long index, Integer k) {
/*  673 */       synchronized (this.sync) {
/*  674 */         return this.list.set(index, k);
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
/*      */     public Integer remove(long i) {
/*  686 */       synchronized (this.sync) {
/*  687 */         return this.list.remove(i);
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
/*      */     public long indexOf(Object o) {
/*  699 */       synchronized (this.sync) {
/*  700 */         return this.list.indexOf(o);
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
/*      */     public long lastIndexOf(Object o) {
/*  712 */       synchronized (this.sync) {
/*  713 */         return this.list.lastIndexOf(o);
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
/*      */   public static IntBigList synchronize(IntBigList l) {
/*  726 */     return new SynchronizedBigList(l);
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
/*      */   public static IntBigList synchronize(IntBigList l, Object sync) {
/*  739 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends IntCollections.UnmodifiableCollection
/*      */     implements IntBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final IntBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(IntBigList l) {
/*  749 */       super(l);
/*  750 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long i) {
/*  755 */       return this.list.getInt(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(long i, int k) {
/*  760 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, int k) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(long i) {
/*  770 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(int k) {
/*  775 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(int k) {
/*  780 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Integer> c) {
/*  785 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, int[][] a, long offset, long length) {
/*  790 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  795 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, int[][] a, long offset, long length) {
/*  800 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, int[][] a) {
/*  805 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void size(long size) {
/*  814 */       this.list.size(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public long size64() {
/*  819 */       return this.list.size64();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator iterator() {
/*  824 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  829 */       return IntBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  834 */       return IntBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  839 */       return IntBigLists.unmodifiable(this.list.subList(from, to));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  844 */       if (o == this) return true; 
/*  845 */       return this.list.equals(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  850 */       return this.list.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends Integer> o) {
/*  855 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, IntCollection c) {
/*  860 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntBigList l) {
/*  865 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, IntBigList l) {
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
/*      */     public Integer get(long i) {
/*  881 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long i, Integer k) {
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
/*      */     public Integer set(long index, Integer k) {
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
/*      */     public Integer remove(long i) {
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
/*      */     public long indexOf(Object o) {
/*  925 */       return this.list.indexOf(o);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long lastIndexOf(Object o) {
/*  936 */       return this.list.lastIndexOf(o);
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
/*      */   public static IntBigList unmodifiable(IntBigList l) {
/*  948 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList
/*      */     extends AbstractIntBigList implements Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final IntList list;
/*      */     
/*      */     protected ListBigList(IntList list) {
/*  957 */       this.list = list;
/*      */     }
/*      */     
/*      */     private int intIndex(long index) {
/*  961 */       if (index >= 2147483647L) throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices"); 
/*  962 */       return (int)index;
/*      */     }
/*      */ 
/*      */     
/*      */     public long size64() {
/*  967 */       return this.list.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public void size(long size) {
/*  972 */       this.list.size(intIndex(size));
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator iterator() {
/*  977 */       return IntBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  982 */       return IntBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long index) {
/*  987 */       return IntBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Integer> c) {
/*  992 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  997 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int key) {
/* 1002 */       return this.list.contains(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] toIntArray() {
/* 1007 */       return this.list.toIntArray();
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/* 1012 */       this.list.removeElements(intIndex(from), intIndex(to));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int[] toIntArray(int[] a) {
/* 1021 */       return this.list.toArray(a);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, IntCollection c) {
/* 1026 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/* 1031 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, IntBigList c) {
/* 1036 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(IntBigList c) {
/* 1041 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(IntCollection c) {
/* 1046 */       return this.list.containsAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(IntCollection c) {
/* 1051 */       return this.list.removeAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(IntCollection c) {
/* 1056 */       return this.list.retainAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, int key) {
/* 1061 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(int key) {
/* 1066 */       return this.list.add(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long index) {
/* 1071 */       return this.list.getInt(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(int k) {
/* 1076 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(int k) {
/* 1081 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(long index) {
/* 1086 */       return this.list.removeInt(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public int set(long index, int k) {
/* 1091 */       return this.list.set(intIndex(index), k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1096 */       return this.list.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public <T> T[] toArray(T[] a) {
/* 1101 */       return (T[])this.list.toArray((Object[])a);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/* 1106 */       return this.list.containsAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Integer> c) {
/* 1111 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/* 1116 */       return this.list.removeAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/* 1121 */       return this.list.retainAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1126 */       this.list.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1131 */       return this.list.hashCode();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigList asBigList(IntList list) {
/* 1142 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */