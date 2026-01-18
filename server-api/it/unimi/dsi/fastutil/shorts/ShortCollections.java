/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterators;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.IntStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShortCollections
/*     */ {
/*     */   public static abstract class EmptyCollection
/*     */     extends AbstractShortCollection
/*     */   {
/*     */     public boolean contains(short k) {
/*  55 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/*  60 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/*  65 */       if (array.length > 0) array[0] = null; 
/*  66 */       return array;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/*  72 */       return ShortIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/*  78 */       return ShortSpliterators.EMPTY_SPLITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  83 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  92 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  97 */       if (o == this) return true; 
/*  98 */       if (!(o instanceof Collection)) return false; 
/*  99 */       return ((Collection)o).isEmpty();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Short> action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 109 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Short> c) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 119 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 124 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean removeIf(Predicate<? super Short> filter) {
/* 130 */       Objects.requireNonNull(filter);
/* 131 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public short[] toShortArray() {
/* 136 */       return ShortArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public short[] toShortArray(short[] a) {
/* 145 */       return a;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(ShortCollection c) {
/* 154 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(ShortCollection c) {
/* 159 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(ShortCollection c) {
/* 164 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(ShortCollection c) {
/* 169 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(ShortPredicate filter) {
/* 174 */       Objects.requireNonNull(filter);
/* 175 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator intIterator() {
/* 180 */       return (IntIterator)IntIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 185 */       return (IntSpliterator)IntSpliterators.EMPTY_SPLITERATOR;
/*     */     }
/*     */   }
/*     */   
/*     */   static class SynchronizedCollection
/*     */     implements ShortCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortCollection collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(ShortCollection c, Object sync) {
/* 196 */       this.collection = Objects.<ShortCollection>requireNonNull(c);
/* 197 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedCollection(ShortCollection c) {
/* 201 */       this.collection = Objects.<ShortCollection>requireNonNull(c);
/* 202 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(short k) {
/* 207 */       synchronized (this.sync) {
/* 208 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(short k) {
/* 214 */       synchronized (this.sync) {
/* 215 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(short k) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 228 */       synchronized (this.sync) {
/* 229 */         return this.collection.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 235 */       synchronized (this.sync) {
/* 236 */         return this.collection.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short[] toShortArray() {
/* 242 */       synchronized (this.sync) {
/* 243 */         return this.collection.toShortArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 249 */       synchronized (this.sync) {
/* 250 */         return this.collection.toArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public short[] toShortArray(short[] a) {
/* 260 */       return toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public short[] toArray(short[] a) {
/* 265 */       synchronized (this.sync) {
/* 266 */         return this.collection.toArray(a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(ShortCollection c) {
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(ShortCollection c) {
/* 279 */       synchronized (this.sync) {
/* 280 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(ShortCollection c) {
/* 286 */       synchronized (this.sync) {
/* 287 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(ShortCollection c) {
/* 293 */       synchronized (this.sync) {
/* 294 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Short k) {
/* 301 */       synchronized (this.sync) {
/* 302 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 309 */       synchronized (this.sync) {
/* 310 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 317 */       synchronized (this.sync) {
/* 318 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator intIterator() {
/* 324 */       return this.collection.intIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 329 */       return this.collection.intSpliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntStream intStream() {
/* 334 */       return this.collection.intStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntStream intParallelStream() {
/* 339 */       return this.collection.intParallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 344 */       synchronized (this.sync) {
/* 345 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 351 */       return this.collection.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 356 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Short> stream() {
/* 362 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Short> parallelStream() {
/* 368 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 373 */       synchronized (this.sync) {
/* 374 */         this.collection.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Short> c) {
/* 380 */       synchronized (this.sync) {
/* 381 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 387 */       synchronized (this.sync) {
/* 388 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 394 */       synchronized (this.sync) {
/* 395 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 401 */       synchronized (this.sync) {
/* 402 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(ShortPredicate filter) {
/* 408 */       synchronized (this.sync) {
/* 409 */         return this.collection.removeIf(filter);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 415 */       synchronized (this.sync) {
/* 416 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 422 */       synchronized (this.sync) {
/* 423 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 429 */       synchronized (this.sync) {
/* 430 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 436 */       if (o == this) return true; 
/* 437 */       synchronized (this.sync) {
/* 438 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 443 */       synchronized (this.sync) {
/* 444 */         s.defaultWriteObject();
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
/*     */   public static ShortCollection synchronize(ShortCollection c) {
/* 457 */     return new SynchronizedCollection(c);
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
/*     */   public static ShortCollection synchronize(ShortCollection c, Object sync) {
/* 470 */     return new SynchronizedCollection(c, sync);
/*     */   }
/*     */   
/*     */   static class UnmodifiableCollection
/*     */     implements ShortCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortCollection collection;
/*     */     
/*     */     protected UnmodifiableCollection(ShortCollection c) {
/* 479 */       this.collection = Objects.<ShortCollection>requireNonNull(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(short k) {
/* 484 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(short k) {
/* 489 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 494 */       return this.collection.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 499 */       return this.collection.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(short o) {
/* 504 */       return this.collection.contains(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 509 */       return ShortIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 514 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Short> stream() {
/* 520 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Short> parallelStream() {
/* 526 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 531 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 536 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 541 */       return this.collection.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 546 */       this.collection.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 551 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Short> c) {
/* 556 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 561 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 566 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(ShortPredicate filter) {
/* 571 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Short k) {
/* 577 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 583 */       return this.collection.contains(k);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 589 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short[] toShortArray() {
/* 594 */       return this.collection.toShortArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public short[] toShortArray(short[] a) {
/* 603 */       return toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public short[] toArray(short[] a) {
/* 608 */       return this.collection.toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(ShortCollection c) {
/* 613 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(ShortCollection c) {
/* 618 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(ShortCollection c) {
/* 623 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(ShortCollection c) {
/* 628 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator intIterator() {
/* 633 */       return this.collection.intIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 638 */       return this.collection.intSpliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntStream intStream() {
/* 643 */       return this.collection.intStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntStream intParallelStream() {
/* 648 */       return this.collection.intParallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 653 */       return this.collection.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 658 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 663 */       if (o == this) return true; 
/* 664 */       return this.collection.equals(o);
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
/*     */   public static ShortCollection unmodifiable(ShortCollection c) {
/* 676 */     return new UnmodifiableCollection(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection
/*     */     extends AbstractShortCollection implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortIterable iterable;
/*     */     
/*     */     protected IterableCollection(ShortIterable iterable) {
/* 685 */       this.iterable = Objects.<ShortIterable>requireNonNull(iterable);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 690 */       long size = this.iterable.spliterator().getExactSizeIfKnown();
/* 691 */       if (size >= 0L) return (int)Math.min(2147483647L, size); 
/* 692 */       int c = 0;
/* 693 */       ShortIterator iterator = iterator();
/* 694 */       while (iterator.hasNext()) {
/* 695 */         iterator.nextShort();
/* 696 */         c++;
/*     */       } 
/* 698 */       return c;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 703 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 708 */       return this.iterable.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 713 */       return this.iterable.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator intIterator() {
/* 718 */       return this.iterable.intIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 723 */       return this.iterable.intSpliterator();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortCollection asCollection(ShortIterable iterable) {
/* 734 */     if (iterable instanceof ShortCollection) return (ShortCollection)iterable;
/*     */     
/* 736 */     return new IterableCollection(iterable);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */