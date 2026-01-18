/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongPredicate;
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
/*      */ 
/*      */ 
/*      */ public final class LongBigLists
/*      */ {
/*      */   public static LongBigList shuffle(LongBigList l, Random random) {
/*   42 */     for (long i = l.size64(); i-- != 0L; ) {
/*   43 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   44 */       long t = l.getLong(i);
/*   45 */       l.set(i, l.getLong(p));
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
/*      */     extends LongCollections.EmptyCollection
/*      */     implements LongBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getLong(long i) {
/*   65 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(long k) {
/*   70 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(long i) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, long k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(long index, long k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(long k) {
/*   90 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(long k) {
/*   95 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Long> c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongCollection c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongBigList c) {
/*  110 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, LongCollection c) {
/*  115 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, LongBigList c) {
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
/*      */     public void add(long index, Long k) {
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
/*      */     public boolean add(Long k) {
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
/*      */     public Long get(long i) {
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
/*      */     public Long set(long index, Long k) {
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
/*      */     public Long remove(long k) {
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
/*      */     public LongBigListIterator listIterator() {
/*  203 */       return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongBigListIterator iterator() {
/*  209 */       return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator(long i) {
/*  215 */       if (i == 0L) return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  216 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/*  222 */       return LongSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigList subList(long from, long to) {
/*  227 */       if (from == 0L && to == 0L) return this; 
/*  228 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, long[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, long[][] a, long offset, long length) {
/*  244 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, long[][] a) {
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
/*      */     public int compareTo(BigList<? extends Long> o) {
/*  264 */       if (o == this) return 0; 
/*  265 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  270 */       return LongBigLists.EMPTY_BIG_LIST;
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
/*  290 */       return LongBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractLongBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final long element;
/*      */ 
/*      */     
/*      */     protected Singleton(long element) {
/*  311 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long i) {
/*  316 */       if (i == 0L) return this.element; 
/*  317 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(long k) {
/*  322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(long i) {
/*  327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/*  332 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(long k) {
/*  337 */       return (k == this.element) ? 0L : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long[] toLongArray() {
/*  343 */       long[] a = { this.element };
/*  344 */       return a;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator() {
/*  349 */       return LongBigListIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator(long i) {
/*  354 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/*  355 */       LongBigListIterator l = listIterator();
/*  356 */       if (i == 1L) l.nextLong(); 
/*  357 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/*  362 */       return LongSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongBigList subList(long from, long to) {
/*  368 */       ensureIndex(from);
/*  369 */       ensureIndex(to);
/*  370 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  371 */       if (from != 0L || to != 1L) return LongBigLists.EMPTY_BIG_LIST; 
/*  372 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Long> c) {
/*  377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Long> c) {
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
/*      */     public boolean addAll(LongBigList c) {
/*  397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, LongBigList c) {
/*  402 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, LongCollection c) {
/*  407 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongCollection c) {
/*  412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(LongCollection c) {
/*  417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(LongCollection c) {
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
/*      */   public static LongBigList singleton(long element) {
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
/*      */   public static LongBigList singleton(Object element) {
/*  460 */     return new Singleton(((Long)element).longValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends LongCollections.SynchronizedCollection
/*      */     implements LongBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final LongBigList list;
/*      */     
/*      */     protected SynchronizedBigList(LongBigList l, Object sync) {
/*  470 */       super(l, sync);
/*  471 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedBigList(LongBigList l) {
/*  475 */       super(l);
/*  476 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long i) {
/*  481 */       synchronized (this.sync) {
/*  482 */         return this.list.getLong(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(long i, long k) {
/*  488 */       synchronized (this.sync) {
/*  489 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, long k) {
/*  495 */       synchronized (this.sync) {
/*  496 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(long i) {
/*  502 */       synchronized (this.sync) {
/*  503 */         return this.list.removeLong(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(long k) {
/*  509 */       synchronized (this.sync) {
/*  510 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(long k) {
/*  516 */       synchronized (this.sync) {
/*  517 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Long> c) {
/*  523 */       synchronized (this.sync) {
/*  524 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, long[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, long[][] a, long offset, long length) {
/*  544 */       synchronized (this.sync) {
/*  545 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, long[][] a) {
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
/*      */     public LongBigListIterator iterator() {
/*  576 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator() {
/*  581 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator(long i) {
/*  586 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigList subList(long from, long to) {
/*  591 */       synchronized (this.sync) {
/*  592 */         return LongBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Long> o) {
/*  613 */       synchronized (this.sync) {
/*  614 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, LongCollection c) {
/*  620 */       synchronized (this.sync) {
/*  621 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, LongBigList l) {
/*  627 */       synchronized (this.sync) {
/*  628 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongBigList l) {
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
/*      */     public void add(long i, Long k) {
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
/*      */     public Long get(long i) {
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
/*      */     public Long set(long index, Long k) {
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
/*      */     public Long remove(long i) {
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
/*      */   public static LongBigList synchronize(LongBigList l) {
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
/*      */   public static LongBigList synchronize(LongBigList l, Object sync) {
/*  739 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends LongCollections.UnmodifiableCollection
/*      */     implements LongBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final LongBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(LongBigList l) {
/*  749 */       super(l);
/*  750 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long i) {
/*  755 */       return this.list.getLong(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(long i, long k) {
/*  760 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, long k) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(long i) {
/*  770 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(long k) {
/*  775 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(long k) {
/*  780 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Long> c) {
/*  785 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, long[][] a, long offset, long length) {
/*  790 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  795 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, long[][] a, long offset, long length) {
/*  800 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, long[][] a) {
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
/*      */     public LongBigListIterator iterator() {
/*  824 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator() {
/*  829 */       return LongBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator(long i) {
/*  834 */       return LongBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigList subList(long from, long to) {
/*  839 */       return LongBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Long> o) {
/*  855 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, LongCollection c) {
/*  860 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongBigList l) {
/*  865 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, LongBigList l) {
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
/*      */     public Long get(long i) {
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
/*      */     public void add(long i, Long k) {
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
/*      */     public Long set(long index, Long k) {
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
/*      */     public Long remove(long i) {
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
/*      */   public static LongBigList unmodifiable(LongBigList l) {
/*  948 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList
/*      */     extends AbstractLongBigList implements Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final LongList list;
/*      */     
/*      */     protected ListBigList(LongList list) {
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
/*      */     public LongBigListIterator iterator() {
/*  977 */       return LongBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator() {
/*  982 */       return LongBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigListIterator listIterator(long index) {
/*  987 */       return LongBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Long> c) {
/*  992 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongBigList subList(long from, long to) {
/*  997 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long key) {
/* 1002 */       return this.list.contains(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public long[] toLongArray() {
/* 1007 */       return this.list.toLongArray();
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
/*      */     public long[] toLongArray(long[] a) {
/* 1021 */       return this.list.toArray(a);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, LongCollection c) {
/* 1026 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongCollection c) {
/* 1031 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, LongBigList c) {
/* 1036 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(LongBigList c) {
/* 1041 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(LongCollection c) {
/* 1046 */       return this.list.containsAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(LongCollection c) {
/* 1051 */       return this.list.removeAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(LongCollection c) {
/* 1056 */       return this.list.retainAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, long key) {
/* 1061 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(long key) {
/* 1066 */       return this.list.add(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long index) {
/* 1071 */       return this.list.getLong(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(long k) {
/* 1076 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(long k) {
/* 1081 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long removeLong(long index) {
/* 1086 */       return this.list.removeLong(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public long set(long index, long k) {
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
/*      */     public boolean addAll(Collection<? extends Long> c) {
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
/*      */   public static LongBigList asBigList(LongList list) {
/* 1142 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */