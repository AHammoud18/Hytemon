/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoublePredicate;
/*      */ import java.util.stream.DoubleStream;
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
/*      */ public final class DoubleBigLists
/*      */ {
/*      */   public static DoubleBigList shuffle(DoubleBigList l, Random random) {
/*   42 */     for (long i = l.size64(); i-- != 0L; ) {
/*   43 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   44 */       double t = l.getDouble(i);
/*   45 */       l.set(i, l.getDouble(p));
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
/*      */     extends DoubleCollections.EmptyCollection
/*      */     implements DoubleBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getDouble(long i) {
/*   65 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(double k) {
/*   70 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public double removeDouble(long i) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, double k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public double set(long index, double k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(double k) {
/*   90 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(double k) {
/*   95 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Double> c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleCollection c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleBigList c) {
/*  110 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, DoubleCollection c) {
/*  115 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, DoubleBigList c) {
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
/*      */     public void add(long index, Double k) {
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
/*      */     public boolean add(Double k) {
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
/*      */     public Double get(long i) {
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
/*      */     public Double set(long index, Double k) {
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
/*      */     public Double remove(long k) {
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
/*      */     public DoubleBigListIterator listIterator() {
/*  203 */       return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator iterator() {
/*  209 */       return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  215 */       if (i == 0L) return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  216 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleSpliterator spliterator() {
/*  222 */       return DoubleSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  227 */       if (from == 0L && to == 0L) return this; 
/*  228 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, double[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, double[][] a, long offset, long length) {
/*  244 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, double[][] a) {
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
/*      */     public int compareTo(BigList<? extends Double> o) {
/*  264 */       if (o == this) return 0; 
/*  265 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  270 */       return DoubleBigLists.EMPTY_BIG_LIST;
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
/*  290 */       return DoubleBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractDoubleBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final double element;
/*      */ 
/*      */     
/*      */     protected Singleton(double element) {
/*  311 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(long i) {
/*  316 */       if (i == 0L) return this.element; 
/*  317 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(double k) {
/*  322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public double removeDouble(long i) {
/*  327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/*  332 */       return (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(double k) {
/*  337 */       return (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element)) ? 0L : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] toDoubleArray() {
/*  343 */       double[] a = { this.element };
/*  344 */       return a;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  349 */       return DoubleBigListIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  354 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/*  355 */       DoubleBigListIterator l = listIterator();
/*  356 */       if (i == 1L) l.nextDouble(); 
/*  357 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator spliterator() {
/*  362 */       return DoubleSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  368 */       ensureIndex(from);
/*  369 */       ensureIndex(to);
/*  370 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  371 */       if (from != 0L || to != 1L) return DoubleBigLists.EMPTY_BIG_LIST; 
/*  372 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Double> c) {
/*  377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Double> c) {
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
/*      */     public boolean addAll(DoubleBigList c) {
/*  397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, DoubleBigList c) {
/*  402 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, DoubleCollection c) {
/*  407 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleCollection c) {
/*  412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(DoubleCollection c) {
/*  417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(DoubleCollection c) {
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
/*      */   public static DoubleBigList singleton(double element) {
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
/*      */   public static DoubleBigList singleton(Object element) {
/*  460 */     return new Singleton(((Double)element).doubleValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends DoubleCollections.SynchronizedCollection
/*      */     implements DoubleBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final DoubleBigList list;
/*      */     
/*      */     protected SynchronizedBigList(DoubleBigList l, Object sync) {
/*  470 */       super(l, sync);
/*  471 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedBigList(DoubleBigList l) {
/*  475 */       super(l);
/*  476 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(long i) {
/*  481 */       synchronized (this.sync) {
/*  482 */         return this.list.getDouble(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public double set(long i, double k) {
/*  488 */       synchronized (this.sync) {
/*  489 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, double k) {
/*  495 */       synchronized (this.sync) {
/*  496 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public double removeDouble(long i) {
/*  502 */       synchronized (this.sync) {
/*  503 */         return this.list.removeDouble(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(double k) {
/*  509 */       synchronized (this.sync) {
/*  510 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(double k) {
/*  516 */       synchronized (this.sync) {
/*  517 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Double> c) {
/*  523 */       synchronized (this.sync) {
/*  524 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, double[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, double[][] a, long offset, long length) {
/*  544 */       synchronized (this.sync) {
/*  545 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, double[][] a) {
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
/*      */     public DoubleBigListIterator iterator() {
/*  576 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  581 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  586 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  591 */       synchronized (this.sync) {
/*  592 */         return DoubleBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Double> o) {
/*  613 */       synchronized (this.sync) {
/*  614 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, DoubleCollection c) {
/*  620 */       synchronized (this.sync) {
/*  621 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, DoubleBigList l) {
/*  627 */       synchronized (this.sync) {
/*  628 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleBigList l) {
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
/*      */     public void add(long i, Double k) {
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
/*      */     public Double get(long i) {
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
/*      */     public Double set(long index, Double k) {
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
/*      */     public Double remove(long i) {
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
/*      */   public static DoubleBigList synchronize(DoubleBigList l) {
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
/*      */   public static DoubleBigList synchronize(DoubleBigList l, Object sync) {
/*  739 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends DoubleCollections.UnmodifiableCollection
/*      */     implements DoubleBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final DoubleBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(DoubleBigList l) {
/*  749 */       super(l);
/*  750 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(long i) {
/*  755 */       return this.list.getDouble(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public double set(long i, double k) {
/*  760 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, double k) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public double removeDouble(long i) {
/*  770 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(double k) {
/*  775 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(double k) {
/*  780 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Double> c) {
/*  785 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, double[][] a, long offset, long length) {
/*  790 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  795 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, double[][] a, long offset, long length) {
/*  800 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, double[][] a) {
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
/*      */     public DoubleBigListIterator iterator() {
/*  824 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  829 */       return DoubleBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  834 */       return DoubleBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  839 */       return DoubleBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Double> o) {
/*  855 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, DoubleCollection c) {
/*  860 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleBigList l) {
/*  865 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, DoubleBigList l) {
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
/*      */     public Double get(long i) {
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
/*      */     public void add(long i, Double k) {
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
/*      */     public Double set(long index, Double k) {
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
/*      */     public Double remove(long i) {
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
/*      */   public static DoubleBigList unmodifiable(DoubleBigList l) {
/*  948 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList
/*      */     extends AbstractDoubleBigList implements Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final DoubleList list;
/*      */     
/*      */     protected ListBigList(DoubleList list) {
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
/*      */     public DoubleBigListIterator iterator() {
/*  977 */       return DoubleBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  982 */       return DoubleBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator(long index) {
/*  987 */       return DoubleBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Double> c) {
/*  992 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  997 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double key) {
/* 1002 */       return this.list.contains(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public double[] toDoubleArray() {
/* 1007 */       return this.list.toDoubleArray();
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
/*      */     public double[] toDoubleArray(double[] a) {
/* 1021 */       return this.list.toArray(a);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, DoubleCollection c) {
/* 1026 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleCollection c) {
/* 1031 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, DoubleBigList c) {
/* 1036 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(DoubleBigList c) {
/* 1041 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(DoubleCollection c) {
/* 1046 */       return this.list.containsAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(DoubleCollection c) {
/* 1051 */       return this.list.removeAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(DoubleCollection c) {
/* 1056 */       return this.list.retainAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, double key) {
/* 1061 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(double key) {
/* 1066 */       return this.list.add(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(long index) {
/* 1071 */       return this.list.getDouble(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(double k) {
/* 1076 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(double k) {
/* 1081 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public double removeDouble(long index) {
/* 1086 */       return this.list.removeDouble(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public double set(long index, double k) {
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
/*      */     public boolean addAll(Collection<? extends Double> c) {
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
/*      */   public static DoubleBigList asBigList(DoubleList list) {
/* 1142 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */