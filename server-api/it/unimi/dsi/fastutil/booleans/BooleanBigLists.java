/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator;
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
/*      */ 
/*      */ public final class BooleanBigLists
/*      */ {
/*      */   public static BooleanBigList shuffle(BooleanBigList l, Random random) {
/*   42 */     for (long i = l.size64(); i-- != 0L; ) {
/*   43 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   44 */       boolean t = l.getBoolean(i);
/*   45 */       l.set(i, l.getBoolean(p));
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
/*      */     extends BooleanCollections.EmptyCollection
/*      */     implements BooleanBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long i) {
/*   65 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(boolean k) {
/*   70 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, boolean k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(long index, boolean k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(boolean k) {
/*   90 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*   95 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Boolean> c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanBigList c) {
/*  110 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, BooleanCollection c) {
/*  115 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, BooleanBigList c) {
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
/*      */     public void add(long index, Boolean k) {
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
/*      */     public boolean add(Boolean k) {
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
/*      */     public Boolean get(long i) {
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
/*      */     public Boolean set(long index, Boolean k) {
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
/*      */     public Boolean remove(long k) {
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
/*      */     public BooleanBigListIterator listIterator() {
/*  203 */       return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator iterator() {
/*  209 */       return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  215 */       if (i == 0L) return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  216 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanSpliterator spliterator() {
/*  222 */       return BooleanSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  227 */       if (from == 0L && to == 0L) return this; 
/*  228 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, boolean[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, boolean[][] a, long offset, long length) {
/*  244 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, boolean[][] a) {
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
/*      */     public int compareTo(BigList<? extends Boolean> o) {
/*  264 */       if (o == this) return 0; 
/*  265 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  270 */       return BooleanBigLists.EMPTY_BIG_LIST;
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
/*  290 */       return BooleanBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractBooleanBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final boolean element;
/*      */ 
/*      */     
/*      */     protected Singleton(boolean element) {
/*  311 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  316 */       if (i == 0L) return this.element; 
/*  317 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(boolean k) {
/*  322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*  327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(boolean k) {
/*  332 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(boolean k) {
/*  337 */       return (k == this.element) ? 0L : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean[] toBooleanArray() {
/*  343 */       boolean[] a = { this.element };
/*  344 */       return a;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  349 */       return BooleanBigListIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  354 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/*  355 */       BooleanBigListIterator l = listIterator();
/*  356 */       if (i == 1L) l.nextBoolean(); 
/*  357 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator spliterator() {
/*  362 */       return BooleanSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  368 */       ensureIndex(from);
/*  369 */       ensureIndex(to);
/*  370 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  371 */       if (from != 0L || to != 1L) return BooleanBigLists.EMPTY_BIG_LIST; 
/*  372 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Boolean> c) {
/*  377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Boolean> c) {
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
/*      */     public boolean addAll(BooleanBigList c) {
/*  397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, BooleanBigList c) {
/*  402 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, BooleanCollection c) {
/*  407 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/*  412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(BooleanCollection c) {
/*  417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(BooleanCollection c) {
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
/*      */   public static BooleanBigList singleton(boolean element) {
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
/*      */   public static BooleanBigList singleton(Object element) {
/*  460 */     return new Singleton(((Boolean)element).booleanValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends BooleanCollections.SynchronizedCollection
/*      */     implements BooleanBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final BooleanBigList list;
/*      */     
/*      */     protected SynchronizedBigList(BooleanBigList l, Object sync) {
/*  470 */       super(l, sync);
/*  471 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedBigList(BooleanBigList l) {
/*  475 */       super(l);
/*  476 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  481 */       synchronized (this.sync) {
/*  482 */         return this.list.getBoolean(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(long i, boolean k) {
/*  488 */       synchronized (this.sync) {
/*  489 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, boolean k) {
/*  495 */       synchronized (this.sync) {
/*  496 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*  502 */       synchronized (this.sync) {
/*  503 */         return this.list.removeBoolean(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(boolean k) {
/*  509 */       synchronized (this.sync) {
/*  510 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*  516 */       synchronized (this.sync) {
/*  517 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Boolean> c) {
/*  523 */       synchronized (this.sync) {
/*  524 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, boolean[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, boolean[][] a, long offset, long length) {
/*  544 */       synchronized (this.sync) {
/*  545 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, boolean[][] a) {
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
/*      */     public BooleanBigListIterator iterator() {
/*  576 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  581 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  586 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  591 */       synchronized (this.sync) {
/*  592 */         return BooleanBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Boolean> o) {
/*  613 */       synchronized (this.sync) {
/*  614 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, BooleanCollection c) {
/*  620 */       synchronized (this.sync) {
/*  621 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, BooleanBigList l) {
/*  627 */       synchronized (this.sync) {
/*  628 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanBigList l) {
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
/*      */     public void add(long i, Boolean k) {
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
/*      */     public Boolean get(long i) {
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
/*      */     public Boolean set(long index, Boolean k) {
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
/*      */     public Boolean remove(long i) {
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
/*      */   public static BooleanBigList synchronize(BooleanBigList l) {
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
/*      */   public static BooleanBigList synchronize(BooleanBigList l, Object sync) {
/*  739 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends BooleanCollections.UnmodifiableCollection
/*      */     implements BooleanBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final BooleanBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(BooleanBigList l) {
/*  749 */       super(l);
/*  750 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  755 */       return this.list.getBoolean(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(long i, boolean k) {
/*  760 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, boolean k) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*  770 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(boolean k) {
/*  775 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*  780 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Boolean> c) {
/*  785 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, boolean[][] a, long offset, long length) {
/*  790 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  795 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, boolean[][] a, long offset, long length) {
/*  800 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, boolean[][] a) {
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
/*      */     public BooleanBigListIterator iterator() {
/*  824 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  829 */       return BooleanBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  834 */       return BooleanBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  839 */       return BooleanBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Boolean> o) {
/*  855 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, BooleanCollection c) {
/*  860 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanBigList l) {
/*  865 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, BooleanBigList l) {
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
/*      */     public Boolean get(long i) {
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
/*      */     public void add(long i, Boolean k) {
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
/*      */     public Boolean set(long index, Boolean k) {
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
/*      */     public Boolean remove(long i) {
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
/*      */   public static BooleanBigList unmodifiable(BooleanBigList l) {
/*  948 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList
/*      */     extends AbstractBooleanBigList implements Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final BooleanList list;
/*      */     
/*      */     protected ListBigList(BooleanList list) {
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
/*      */     public BooleanBigListIterator iterator() {
/*  977 */       return BooleanBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  982 */       return BooleanBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long index) {
/*  987 */       return BooleanBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Boolean> c) {
/*  992 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  997 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(boolean key) {
/* 1002 */       return this.list.contains(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean[] toBooleanArray() {
/* 1007 */       return this.list.toBooleanArray();
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
/*      */     public boolean[] toBooleanArray(boolean[] a) {
/* 1021 */       return this.list.toArray(a);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, BooleanCollection c) {
/* 1026 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/* 1031 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, BooleanBigList c) {
/* 1036 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(BooleanBigList c) {
/* 1041 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(BooleanCollection c) {
/* 1046 */       return this.list.containsAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(BooleanCollection c) {
/* 1051 */       return this.list.removeAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(BooleanCollection c) {
/* 1056 */       return this.list.retainAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, boolean key) {
/* 1061 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(boolean key) {
/* 1066 */       return this.list.add(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long index) {
/* 1071 */       return this.list.getBoolean(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(boolean k) {
/* 1076 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/* 1081 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(long index) {
/* 1086 */       return this.list.removeBoolean(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean set(long index, boolean k) {
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
/*      */     public boolean addAll(Collection<? extends Boolean> c) {
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
/*      */   public static BooleanBigList asBigList(BooleanList list) {
/* 1142 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */