/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator;
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
/*      */ public final class FloatBigLists
/*      */ {
/*      */   public static FloatBigList shuffle(FloatBigList l, Random random) {
/*   42 */     for (long i = l.size64(); i-- != 0L; ) {
/*   43 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   44 */       float t = l.getFloat(i);
/*   45 */       l.set(i, l.getFloat(p));
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
/*      */     extends FloatCollections.EmptyCollection
/*      */     implements FloatBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getFloat(long i) {
/*   65 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(float k) {
/*   70 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(long i) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, float k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public float set(long index, float k) {
/*   85 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(float k) {
/*   90 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(float k) {
/*   95 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Float> c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatCollection c) {
/*  105 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatBigList c) {
/*  110 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, FloatCollection c) {
/*  115 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, FloatBigList c) {
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
/*      */     public void add(long index, Float k) {
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
/*      */     public boolean add(Float k) {
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
/*      */     public Float get(long i) {
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
/*      */     public Float set(long index, Float k) {
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
/*      */     public Float remove(long k) {
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
/*      */     public FloatBigListIterator listIterator() {
/*  203 */       return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatBigListIterator iterator() {
/*  209 */       return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator(long i) {
/*  215 */       if (i == 0L) return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  216 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/*  222 */       return FloatSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigList subList(long from, long to) {
/*  227 */       if (from == 0L && to == 0L) return this; 
/*  228 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, float[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, float[][] a, long offset, long length) {
/*  244 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, float[][] a) {
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
/*      */     public int compareTo(BigList<? extends Float> o) {
/*  264 */       if (o == this) return 0; 
/*  265 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  270 */       return FloatBigLists.EMPTY_BIG_LIST;
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
/*  290 */       return FloatBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractFloatBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final float element;
/*      */ 
/*      */     
/*      */     protected Singleton(float element) {
/*  311 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(long i) {
/*  316 */       if (i == 0L) return this.element; 
/*  317 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rem(float k) {
/*  322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(long i) {
/*  327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/*  332 */       return (Float.floatToIntBits(k) == Float.floatToIntBits(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(float k) {
/*  337 */       return (Float.floatToIntBits(k) == Float.floatToIntBits(this.element)) ? 0L : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float[] toFloatArray() {
/*  343 */       float[] a = { this.element };
/*  344 */       return a;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator() {
/*  349 */       return FloatBigListIterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator(long i) {
/*  354 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/*  355 */       FloatBigListIterator l = listIterator();
/*  356 */       if (i == 1L) l.nextFloat(); 
/*  357 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/*  362 */       return FloatSpliterators.singleton(this.element);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatBigList subList(long from, long to) {
/*  368 */       ensureIndex(from);
/*  369 */       ensureIndex(to);
/*  370 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  371 */       if (from != 0L || to != 1L) return FloatBigLists.EMPTY_BIG_LIST; 
/*  372 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Float> c) {
/*  377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Float> c) {
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
/*      */     public boolean addAll(FloatBigList c) {
/*  397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, FloatBigList c) {
/*  402 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long i, FloatCollection c) {
/*  407 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatCollection c) {
/*  412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(FloatCollection c) {
/*  417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(FloatCollection c) {
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
/*      */   public static FloatBigList singleton(float element) {
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
/*      */   public static FloatBigList singleton(Object element) {
/*  460 */     return new Singleton(((Float)element).floatValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends FloatCollections.SynchronizedCollection
/*      */     implements FloatBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final FloatBigList list;
/*      */     
/*      */     protected SynchronizedBigList(FloatBigList l, Object sync) {
/*  470 */       super(l, sync);
/*  471 */       this.list = l;
/*      */     }
/*      */     
/*      */     protected SynchronizedBigList(FloatBigList l) {
/*  475 */       super(l);
/*  476 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(long i) {
/*  481 */       synchronized (this.sync) {
/*  482 */         return this.list.getFloat(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public float set(long i, float k) {
/*  488 */       synchronized (this.sync) {
/*  489 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, float k) {
/*  495 */       synchronized (this.sync) {
/*  496 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(long i) {
/*  502 */       synchronized (this.sync) {
/*  503 */         return this.list.removeFloat(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(float k) {
/*  509 */       synchronized (this.sync) {
/*  510 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(float k) {
/*  516 */       synchronized (this.sync) {
/*  517 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Float> c) {
/*  523 */       synchronized (this.sync) {
/*  524 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, float[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, float[][] a, long offset, long length) {
/*  544 */       synchronized (this.sync) {
/*  545 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, float[][] a) {
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
/*      */     public FloatBigListIterator iterator() {
/*  576 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator() {
/*  581 */       return this.list.listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator(long i) {
/*  586 */       return this.list.listIterator(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigList subList(long from, long to) {
/*  591 */       synchronized (this.sync) {
/*  592 */         return FloatBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Float> o) {
/*  613 */       synchronized (this.sync) {
/*  614 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, FloatCollection c) {
/*  620 */       synchronized (this.sync) {
/*  621 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, FloatBigList l) {
/*  627 */       synchronized (this.sync) {
/*  628 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatBigList l) {
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
/*      */     public void add(long i, Float k) {
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
/*      */     public Float get(long i) {
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
/*      */     public Float set(long index, Float k) {
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
/*      */     public Float remove(long i) {
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
/*      */   public static FloatBigList synchronize(FloatBigList l) {
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
/*      */   public static FloatBigList synchronize(FloatBigList l, Object sync) {
/*  739 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends FloatCollections.UnmodifiableCollection
/*      */     implements FloatBigList, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final FloatBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(FloatBigList l) {
/*  749 */       super(l);
/*  750 */       this.list = l;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(long i) {
/*  755 */       return this.list.getFloat(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public float set(long i, float k) {
/*  760 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long i, float k) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(long i) {
/*  770 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(float k) {
/*  775 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(float k) {
/*  780 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Float> c) {
/*  785 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void getElements(long from, float[][] a, long offset, long length) {
/*  790 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  795 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, float[][] a, long offset, long length) {
/*  800 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void addElements(long index, float[][] a) {
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
/*      */     public FloatBigListIterator iterator() {
/*  824 */       return listIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator() {
/*  829 */       return FloatBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator(long i) {
/*  834 */       return FloatBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigList subList(long from, long to) {
/*  839 */       return FloatBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Float> o) {
/*  855 */       return this.list.compareTo(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, FloatCollection c) {
/*  860 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatBigList l) {
/*  865 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, FloatBigList l) {
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
/*      */     public Float get(long i) {
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
/*      */     public void add(long i, Float k) {
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
/*      */     public Float set(long index, Float k) {
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
/*      */     public Float remove(long i) {
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
/*      */   public static FloatBigList unmodifiable(FloatBigList l) {
/*  948 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList
/*      */     extends AbstractFloatBigList implements Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final FloatList list;
/*      */     
/*      */     protected ListBigList(FloatList list) {
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
/*      */     public FloatBigListIterator iterator() {
/*  977 */       return FloatBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator() {
/*  982 */       return FloatBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigListIterator listIterator(long index) {
/*  987 */       return FloatBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Float> c) {
/*  992 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBigList subList(long from, long to) {
/*  997 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float key) {
/* 1002 */       return this.list.contains(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toFloatArray() {
/* 1007 */       return this.list.toFloatArray();
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
/*      */     public float[] toFloatArray(float[] a) {
/* 1021 */       return this.list.toArray(a);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, FloatCollection c) {
/* 1026 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatCollection c) {
/* 1031 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(long index, FloatBigList c) {
/* 1036 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(FloatBigList c) {
/* 1041 */       return this.list.addAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(FloatCollection c) {
/* 1046 */       return this.list.containsAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(FloatCollection c) {
/* 1051 */       return this.list.removeAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(FloatCollection c) {
/* 1056 */       return this.list.retainAll(c);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long index, float key) {
/* 1061 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(float key) {
/* 1066 */       return this.list.add(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(long index) {
/* 1071 */       return this.list.getFloat(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public long indexOf(float k) {
/* 1076 */       return this.list.indexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastIndexOf(float k) {
/* 1081 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(long index) {
/* 1086 */       return this.list.removeFloat(intIndex(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public float set(long index, float k) {
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
/*      */     public boolean addAll(Collection<? extends Float> c) {
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
/*      */   public static FloatBigList asBigList(FloatList list) {
/* 1142 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */