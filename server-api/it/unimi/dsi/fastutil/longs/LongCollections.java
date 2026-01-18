/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.LongStream;
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
/*     */ public final class LongCollections
/*     */ {
/*     */   public static abstract class EmptyCollection
/*     */     extends AbstractLongCollection
/*     */   {
/*     */     public boolean contains(long k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/*  59 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/*  64 */       if (array.length > 0) array[0] = null; 
/*  65 */       return array;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/*  71 */       return LongIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/*  77 */       return LongSpliterators.EMPTY_SPLITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  82 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  91 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  96 */       if (o == this) return true; 
/*  97 */       if (!(o instanceof Collection)) return false; 
/*  98 */       return ((Collection)o).isEmpty();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Long> action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 108 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Long> c) {
/* 113 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 118 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 123 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean removeIf(Predicate<? super Long> filter) {
/* 129 */       Objects.requireNonNull(filter);
/* 130 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toLongArray() {
/* 135 */       return LongArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public long[] toLongArray(long[] a) {
/* 144 */       return a;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(LongCollection c) {
/* 153 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(LongCollection c) {
/* 158 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(LongCollection c) {
/* 163 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(LongCollection c) {
/* 168 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(LongPredicate filter) {
/* 173 */       Objects.requireNonNull(filter);
/* 174 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class SynchronizedCollection
/*     */     implements LongCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongCollection collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(LongCollection c, Object sync) {
/* 185 */       this.collection = Objects.<LongCollection>requireNonNull(c);
/* 186 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedCollection(LongCollection c) {
/* 190 */       this.collection = Objects.<LongCollection>requireNonNull(c);
/* 191 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(long k) {
/* 196 */       synchronized (this.sync) {
/* 197 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long k) {
/* 203 */       synchronized (this.sync) {
/* 204 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(long k) {
/* 210 */       synchronized (this.sync) {
/* 211 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 217 */       synchronized (this.sync) {
/* 218 */         return this.collection.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 224 */       synchronized (this.sync) {
/* 225 */         return this.collection.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toLongArray() {
/* 231 */       synchronized (this.sync) {
/* 232 */         return this.collection.toLongArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 238 */       synchronized (this.sync) {
/* 239 */         return this.collection.toArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public long[] toLongArray(long[] a) {
/* 249 */       return toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toArray(long[] a) {
/* 254 */       synchronized (this.sync) {
/* 255 */         return this.collection.toArray(a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(LongCollection c) {
/* 261 */       synchronized (this.sync) {
/* 262 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(LongCollection c) {
/* 268 */       synchronized (this.sync) {
/* 269 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(LongCollection c) {
/* 275 */       synchronized (this.sync) {
/* 276 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(LongCollection c) {
/* 282 */       synchronized (this.sync) {
/* 283 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Long k) {
/* 290 */       synchronized (this.sync) {
/* 291 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 298 */       synchronized (this.sync) {
/* 299 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 306 */       synchronized (this.sync) {
/* 307 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator longIterator() {
/* 313 */       return this.collection.longIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator longSpliterator() {
/* 318 */       return this.collection.longSpliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongStream longStream() {
/* 323 */       return this.collection.longStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongStream longParallelStream() {
/* 328 */       return this.collection.longParallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 333 */       synchronized (this.sync) {
/* 334 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 340 */       return this.collection.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 345 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Long> stream() {
/* 351 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Long> parallelStream() {
/* 357 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 362 */       synchronized (this.sync) {
/* 363 */         this.collection.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Long> c) {
/* 369 */       synchronized (this.sync) {
/* 370 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 376 */       synchronized (this.sync) {
/* 377 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 383 */       synchronized (this.sync) {
/* 384 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 390 */       synchronized (this.sync) {
/* 391 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(LongPredicate filter) {
/* 397 */       synchronized (this.sync) {
/* 398 */         return this.collection.removeIf(filter);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 404 */       synchronized (this.sync) {
/* 405 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 411 */       synchronized (this.sync) {
/* 412 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 418 */       synchronized (this.sync) {
/* 419 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 425 */       if (o == this) return true; 
/* 426 */       synchronized (this.sync) {
/* 427 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 432 */       synchronized (this.sync) {
/* 433 */         s.defaultWriteObject();
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
/*     */   public static LongCollection synchronize(LongCollection c) {
/* 446 */     return new SynchronizedCollection(c);
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
/*     */   public static LongCollection synchronize(LongCollection c, Object sync) {
/* 459 */     return new SynchronizedCollection(c, sync);
/*     */   }
/*     */   
/*     */   static class UnmodifiableCollection
/*     */     implements LongCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongCollection collection;
/*     */     
/*     */     protected UnmodifiableCollection(LongCollection c) {
/* 468 */       this.collection = Objects.<LongCollection>requireNonNull(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(long k) {
/* 473 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(long k) {
/* 478 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 483 */       return this.collection.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 488 */       return this.collection.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long o) {
/* 493 */       return this.collection.contains(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 498 */       return LongIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 503 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Long> stream() {
/* 509 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Stream<Long> parallelStream() {
/* 515 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 520 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 525 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 530 */       return this.collection.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 535 */       this.collection.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 540 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Long> c) {
/* 545 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 550 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 555 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(LongPredicate filter) {
/* 560 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Long k) {
/* 566 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 572 */       return this.collection.contains(k);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 578 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toLongArray() {
/* 583 */       return this.collection.toLongArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public long[] toLongArray(long[] a) {
/* 592 */       return toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toArray(long[] a) {
/* 597 */       return this.collection.toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(LongCollection c) {
/* 602 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(LongCollection c) {
/* 607 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(LongCollection c) {
/* 612 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(LongCollection c) {
/* 617 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator longIterator() {
/* 622 */       return this.collection.longIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator longSpliterator() {
/* 627 */       return this.collection.longSpliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongStream longStream() {
/* 632 */       return this.collection.longStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongStream longParallelStream() {
/* 637 */       return this.collection.longParallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 642 */       return this.collection.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 647 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 652 */       if (o == this) return true; 
/* 653 */       return this.collection.equals(o);
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
/*     */   public static LongCollection unmodifiable(LongCollection c) {
/* 665 */     return new UnmodifiableCollection(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection
/*     */     extends AbstractLongCollection implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongIterable iterable;
/*     */     
/*     */     protected IterableCollection(LongIterable iterable) {
/* 674 */       this.iterable = Objects.<LongIterable>requireNonNull(iterable);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 679 */       long size = this.iterable.spliterator().getExactSizeIfKnown();
/* 680 */       if (size >= 0L) return (int)Math.min(2147483647L, size); 
/* 681 */       int c = 0;
/* 682 */       LongIterator iterator = iterator();
/* 683 */       while (iterator.hasNext()) {
/* 684 */         iterator.nextLong();
/* 685 */         c++;
/*     */       } 
/* 687 */       return c;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 692 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 697 */       return this.iterable.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 702 */       return this.iterable.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator longIterator() {
/* 707 */       return this.iterable.longIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator longSpliterator() {
/* 712 */       return this.iterable.longSpliterator();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongCollection asCollection(LongIterable iterable) {
/* 723 */     if (iterable instanceof LongCollection) return (LongCollection)iterable;
/*     */     
/* 725 */     return new IterableCollection(iterable);
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
/*     */   
/*     */   static class SizeDecreasingSupplier<C extends LongCollection>
/*     */     implements Supplier<C>
/*     */   {
/*     */     static final int RECOMMENDED_MIN_SIZE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 751 */     final AtomicInteger suppliedCount = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int expectedFinalSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final IntFunction<C> builder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SizeDecreasingSupplier(int expectedFinalSize, IntFunction<C> builder) {
/* 778 */       this.expectedFinalSize = expectedFinalSize;
/* 779 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public C get() {
/* 790 */       int expectedNeededNextSize = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
/* 791 */       if (expectedNeededNextSize < 0)
/*     */       {
/* 793 */         expectedNeededNextSize = 8;
/*     */       }
/* 795 */       return this.builder.apply(expectedNeededNextSize);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */