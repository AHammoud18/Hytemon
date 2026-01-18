/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReferenceBigLists
/*     */ {
/*     */   public static <K> ReferenceBigList<K> shuffle(ReferenceBigList<K> l, Random random) {
/*  42 */     for (long i = l.size64(); i-- != 0L; ) {
/*  43 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*  44 */       K t = (K)l.get(i);
/*  45 */       l.set(i, l.get(p));
/*  46 */       l.set(p, t);
/*     */     } 
/*  48 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyBigList<K>
/*     */     extends ReferenceCollections.EmptyCollection<K>
/*     */     implements ReferenceBigList<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public K get(long i) {
/*  65 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/*  70 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/*  75 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, K k) {
/*  80 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long index, K k) {
/*  85 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/*  90 */       return -1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/*  95 */       return -1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long i, Collection<? extends K> c) {
/* 100 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 106 */       return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 112 */       return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 118 */       if (i == 0L) return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/* 119 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 125 */       return ObjectSpliterators.EMPTY_SPLITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 130 */       if (from == 0L && to == 0L) return this; 
/* 131 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 136 */       BigArrays.ensureOffsetLength(a, offset, length);
/* 137 */       if (from != 0L) throw new IndexOutOfBoundsException();
/*     */     
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 142 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 147 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 152 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void size(long s) {
/* 157 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 162 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 167 */       return ReferenceBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 172 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 178 */       return (o instanceof BigList && ((BigList)o).isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 183 */       return "[]";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 187 */       return ReferenceBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> emptyList() {
/* 207 */     return EMPTY_BIG_LIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractReferenceBigList<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     private final K element;
/*     */ 
/*     */     
/*     */     protected Singleton(K element) {
/* 221 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long i) {
/* 226 */       if (i == 0L) return this.element; 
/* 227 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 232 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/* 237 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object k) {
/* 242 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 247 */       return (k == this.element) ? 0L : -1L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 253 */       Object[] a = { this.element };
/* 254 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 259 */       return ObjectBigListIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 264 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/* 265 */       ObjectBigListIterator<K> l = listIterator();
/* 266 */       if (i == 1L) l.next(); 
/* 267 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 272 */       return ObjectSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 278 */       ensureIndex(from);
/* 279 */       ensureIndex(to);
/* 280 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 281 */       if (from != 0L || to != 1L) return ReferenceBigLists.EMPTY_BIG_LIST; 
/* 282 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long i, Collection<? extends K> c) {
/* 287 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 292 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 297 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 302 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 307 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 312 */       return 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 317 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> singleton(K element) {
/* 329 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedBigList<K>
/*     */     extends ReferenceCollections.SynchronizedCollection<K>
/*     */     implements ReferenceBigList<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceBigList<K> list;
/*     */     
/*     */     protected SynchronizedBigList(ReferenceBigList<K> l, Object sync) {
/* 339 */       super(l, sync);
/* 340 */       this.list = l;
/*     */     }
/*     */     
/*     */     protected SynchronizedBigList(ReferenceBigList<K> l) {
/* 344 */       super(l);
/* 345 */       this.list = l;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long i) {
/* 350 */       synchronized (this.sync) {
/* 351 */         return (K)this.list.get(i);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long i, K k) {
/* 357 */       synchronized (this.sync) {
/* 358 */         return (K)this.list.set(i, k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long i, K k) {
/* 364 */       synchronized (this.sync) {
/* 365 */         this.list.add(i, k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/* 371 */       synchronized (this.sync) {
/* 372 */         return (K)this.list.remove(i);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 378 */       synchronized (this.sync) {
/* 379 */         return this.list.indexOf(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.list.lastIndexOf(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 392 */       synchronized (this.sync) {
/* 393 */         return this.list.addAll(index, c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 399 */       synchronized (this.sync) {
/* 400 */         this.list.getElements(from, a, offset, length);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 406 */       synchronized (this.sync) {
/* 407 */         this.list.removeElements(from, to);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 413 */       synchronized (this.sync) {
/* 414 */         this.list.addElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 420 */       synchronized (this.sync) {
/* 421 */         this.list.addElements(index, a);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 431 */       synchronized (this.sync) {
/* 432 */         this.list.size(size);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.list.size64();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 445 */       return this.list.listIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 450 */       return this.list.listIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 455 */       return this.list.listIterator(i);
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 460 */       synchronized (this.sync) {
/* 461 */         return ReferenceBigLists.synchronize(this.list.subList(from, to), this.sync);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 467 */       if (o == this) return true; 
/* 468 */       synchronized (this.sync) {
/* 469 */         return this.list.equals(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 475 */       synchronized (this.sync) {
/* 476 */         return this.list.hashCode();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> synchronize(ReferenceBigList<K> l) {
/* 489 */     return new SynchronizedBigList<>(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> synchronize(ReferenceBigList<K> l, Object sync) {
/* 502 */     return new SynchronizedBigList<>(l, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigList<K>
/*     */     extends ReferenceCollections.UnmodifiableCollection<K>
/*     */     implements ReferenceBigList<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceBigList<? extends K> list;
/*     */     
/*     */     protected UnmodifiableBigList(ReferenceBigList<? extends K> l) {
/* 512 */       super(l);
/* 513 */       this.list = l;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long i) {
/* 518 */       return (K)this.list.get(i);
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long i, K k) {
/* 523 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long i, K k) {
/* 528 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/* 533 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 538 */       return this.list.indexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 543 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 548 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 553 */       this.list.getElements(from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 558 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 563 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 568 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 577 */       this.list.size(size);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 582 */       return this.list.size64();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 587 */       return listIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 592 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 597 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator(i));
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 602 */       return ReferenceBigLists.unmodifiable(this.list.subList(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 607 */       if (o == this) return true; 
/* 608 */       return this.list.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 613 */       return this.list.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> unmodifiable(ReferenceBigList<? extends K> l) {
/* 625 */     return new UnmodifiableBigList<>(l);
/*     */   }
/*     */   
/*     */   public static class ListBigList<K>
/*     */     extends AbstractReferenceBigList<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     private final ReferenceList<K> list;
/*     */     
/*     */     protected ListBigList(ReferenceList<K> list) {
/* 634 */       this.list = list;
/*     */     }
/*     */     
/*     */     private int intIndex(long index) {
/* 638 */       if (index >= 2147483647L) throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices"); 
/* 639 */       return (int)index;
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 644 */       return this.list.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void size(long size) {
/* 649 */       this.list.size(intIndex(size));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 654 */       return ObjectBigListIterators.asBigListIterator(this.list.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 659 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long index) {
/* 664 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 669 */       return this.list.addAll(intIndex(index), c);
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 674 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object key) {
/* 679 */       return this.list.contains(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 684 */       return this.list.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 689 */       this.list.removeElements(intIndex(from), intIndex(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, K key) {
/* 694 */       this.list.add(intIndex(index), key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K key) {
/* 699 */       return this.list.add(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long index) {
/* 704 */       return this.list.get(intIndex(index));
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 709 */       return this.list.indexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 714 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long index) {
/* 719 */       return this.list.remove(intIndex(index));
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long index, K k) {
/* 724 */       return this.list.set(intIndex(index), k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 729 */       return this.list.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 734 */       return (T[])this.list.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 739 */       return this.list.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 744 */       return this.list.addAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 749 */       return this.list.removeAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 754 */       return this.list.retainAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 759 */       this.list.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 764 */       return this.list.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> asBigList(ReferenceList<K> list) {
/* 775 */     return new ListBigList<>(list);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */