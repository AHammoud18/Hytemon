/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
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
/*     */ public final class ObjectBigLists
/*     */ {
/*     */   public static <K> ObjectBigList<K> shuffle(ObjectBigList<K> l, Random random) {
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
/*     */     extends ObjectCollections.EmptyCollection<K>
/*     */     implements ObjectBigList<K>, Serializable, Cloneable
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
/*     */     public ObjectBigList<K> subList(long from, long to) {
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
/*     */     public int compareTo(BigList<? extends K> o) {
/* 167 */       if (o == this) return 0; 
/* 168 */       return o.isEmpty() ? 0 : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 173 */       return ObjectBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 178 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 184 */       return (o instanceof BigList && ((BigList)o).isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 189 */       return "[]";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 193 */       return ObjectBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();
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
/*     */   public static <K> ObjectBigList<K> emptyList() {
/* 213 */     return EMPTY_BIG_LIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractObjectBigList<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     private final K element;
/*     */ 
/*     */     
/*     */     protected Singleton(K element) {
/* 227 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long i) {
/* 232 */       if (i == 0L) return this.element; 
/* 233 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 238 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/* 243 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object k) {
/* 248 */       return Objects.equals(k, this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 253 */       return Objects.equals(k, this.element) ? 0L : -1L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 259 */       Object[] a = { this.element };
/* 260 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 265 */       return ObjectBigListIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 270 */       if (i > 1L || i < 0L) throw new IndexOutOfBoundsException(); 
/* 271 */       ObjectBigListIterator<K> l = listIterator();
/* 272 */       if (i == 1L) l.next(); 
/* 273 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 278 */       return ObjectSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 284 */       ensureIndex(from);
/* 285 */       ensureIndex(to);
/* 286 */       if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 287 */       if (from != 0L || to != 1L) return ObjectBigLists.EMPTY_BIG_LIST; 
/* 288 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long i, Collection<? extends K> c) {
/* 293 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 298 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 303 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 308 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 313 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 318 */       return 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 323 */       return this;
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
/*     */   public static <K> ObjectBigList<K> singleton(K element) {
/* 335 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedBigList<K>
/*     */     extends ObjectCollections.SynchronizedCollection<K>
/*     */     implements ObjectBigList<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectBigList<K> list;
/*     */     
/*     */     protected SynchronizedBigList(ObjectBigList<K> l, Object sync) {
/* 345 */       super(l, sync);
/* 346 */       this.list = l;
/*     */     }
/*     */     
/*     */     protected SynchronizedBigList(ObjectBigList<K> l) {
/* 350 */       super(l);
/* 351 */       this.list = l;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long i) {
/* 356 */       synchronized (this.sync) {
/* 357 */         return (K)this.list.get(i);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long i, K k) {
/* 363 */       synchronized (this.sync) {
/* 364 */         return (K)this.list.set(i, k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long i, K k) {
/* 370 */       synchronized (this.sync) {
/* 371 */         this.list.add(i, k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/* 377 */       synchronized (this.sync) {
/* 378 */         return (K)this.list.remove(i);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 384 */       synchronized (this.sync) {
/* 385 */         return this.list.indexOf(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 391 */       synchronized (this.sync) {
/* 392 */         return this.list.lastIndexOf(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 398 */       synchronized (this.sync) {
/* 399 */         return this.list.addAll(index, c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 405 */       synchronized (this.sync) {
/* 406 */         this.list.getElements(from, a, offset, length);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 412 */       synchronized (this.sync) {
/* 413 */         this.list.removeElements(from, to);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 419 */       synchronized (this.sync) {
/* 420 */         this.list.addElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.list.addElements(index, a);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 437 */       synchronized (this.sync) {
/* 438 */         this.list.size(size);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.list.size64();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 451 */       return this.list.listIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 456 */       return this.list.listIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 461 */       return this.list.listIterator(i);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return ObjectBigLists.synchronize(this.list.subList(from, to), this.sync);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 473 */       if (o == this) return true; 
/* 474 */       synchronized (this.sync) {
/* 475 */         return this.list.equals(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 481 */       synchronized (this.sync) {
/* 482 */         return this.list.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(BigList<? extends K> o) {
/* 488 */       synchronized (this.sync) {
/* 489 */         return this.list.compareTo(o);
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
/*     */   public static <K> ObjectBigList<K> synchronize(ObjectBigList<K> l) {
/* 502 */     return new SynchronizedBigList<>(l);
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
/*     */   public static <K> ObjectBigList<K> synchronize(ObjectBigList<K> l, Object sync) {
/* 515 */     return new SynchronizedBigList<>(l, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigList<K>
/*     */     extends ObjectCollections.UnmodifiableCollection<K>
/*     */     implements ObjectBigList<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectBigList<? extends K> list;
/*     */     
/*     */     protected UnmodifiableBigList(ObjectBigList<? extends K> l) {
/* 525 */       super(l);
/* 526 */       this.list = l;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long i) {
/* 531 */       return (K)this.list.get(i);
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long i, K k) {
/* 536 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long i, K k) {
/* 541 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long i) {
/* 546 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 551 */       return this.list.indexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 556 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 561 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 566 */       this.list.getElements(from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 571 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 576 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 581 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 590 */       this.list.size(size);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 595 */       return this.list.size64();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 600 */       return listIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 605 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 610 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator(i));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 615 */       return ObjectBigLists.unmodifiable(this.list.subList(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 620 */       if (o == this) return true; 
/* 621 */       return this.list.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 626 */       return this.list.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(BigList<? extends K> o) {
/* 632 */       return this.list.compareTo(o);
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
/*     */   public static <K> ObjectBigList<K> unmodifiable(ObjectBigList<? extends K> l) {
/* 644 */     return new UnmodifiableBigList<>(l);
/*     */   }
/*     */   
/*     */   public static class ListBigList<K>
/*     */     extends AbstractObjectBigList<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     private final ObjectList<K> list;
/*     */     
/*     */     protected ListBigList(ObjectList<K> list) {
/* 653 */       this.list = list;
/*     */     }
/*     */     
/*     */     private int intIndex(long index) {
/* 657 */       if (index >= 2147483647L) throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices"); 
/* 658 */       return (int)index;
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 663 */       return this.list.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void size(long size) {
/* 668 */       this.list.size(intIndex(size));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 673 */       return ObjectBigListIterators.asBigListIterator(this.list.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 678 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long index) {
/* 683 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 688 */       return this.list.addAll(intIndex(index), c);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 693 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object key) {
/* 698 */       return this.list.contains(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 703 */       return this.list.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 708 */       this.list.removeElements(intIndex(from), intIndex(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, K key) {
/* 713 */       this.list.add(intIndex(index), key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K key) {
/* 718 */       return this.list.add(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long index) {
/* 723 */       return this.list.get(intIndex(index));
/*     */     }
/*     */ 
/*     */     
/*     */     public long indexOf(Object k) {
/* 728 */       return this.list.indexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 733 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long index) {
/* 738 */       return this.list.remove(intIndex(index));
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long index, K k) {
/* 743 */       return this.list.set(intIndex(index), k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 748 */       return this.list.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 753 */       return (T[])this.list.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 758 */       return this.list.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 763 */       return this.list.addAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 768 */       return this.list.removeAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 773 */       return this.list.retainAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 778 */       this.list.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 783 */       return this.list.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigList<K> asBigList(ObjectList<K> list) {
/* 794 */     return new ListBigList<>(list);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */