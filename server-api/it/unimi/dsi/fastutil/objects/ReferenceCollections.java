/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
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
/*     */ public final class ReferenceCollections
/*     */ {
/*     */   public static abstract class EmptyCollection<K>
/*     */     extends AbstractReferenceCollection<K>
/*     */   {
/*     */     public boolean contains(Object k) {
/*  48 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/*  53 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/*  58 */       if (array.length > 0) array[0] = null; 
/*  59 */       return array;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/*  65 */       return ObjectIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/*  71 */       return ObjectSpliterators.EMPTY_SPLITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  76 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  85 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  90 */       if (o == this) return true; 
/*  91 */       if (!(o instanceof Collection)) return false; 
/*  92 */       return ((Collection)o).isEmpty();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 101 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 106 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 111 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 116 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(Predicate<? super K> filter) {
/* 121 */       Objects.requireNonNull(filter);
/* 122 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class SynchronizedCollection<K>
/*     */     implements ReferenceCollection<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceCollection<K> collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(ReferenceCollection<K> c, Object sync) {
/* 133 */       this.collection = Objects.<ReferenceCollection<K>>requireNonNull(c);
/* 134 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedCollection(ReferenceCollection<K> c) {
/* 138 */       this.collection = Objects.<ReferenceCollection<K>>requireNonNull(c);
/* 139 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K k) {
/* 144 */       synchronized (this.sync) {
/* 145 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object k) {
/* 151 */       synchronized (this.sync) {
/* 152 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 158 */       synchronized (this.sync) {
/* 159 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 165 */       synchronized (this.sync) {
/* 166 */         return this.collection.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 172 */       synchronized (this.sync) {
/* 173 */         return this.collection.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 179 */       synchronized (this.sync) {
/* 180 */         return this.collection.toArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 186 */       synchronized (this.sync) {
/* 187 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 193 */       return this.collection.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 198 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<K> stream() {
/* 203 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<K> parallelStream() {
/* 208 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 213 */       synchronized (this.sync) {
/* 214 */         this.collection.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 220 */       synchronized (this.sync) {
/* 221 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 227 */       synchronized (this.sync) {
/* 228 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 241 */       synchronized (this.sync) {
/* 242 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(Predicate<? super K> filter) {
/* 248 */       synchronized (this.sync) {
/* 249 */         return this.collection.removeIf(filter);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 255 */       synchronized (this.sync) {
/* 256 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 262 */       synchronized (this.sync) {
/* 263 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 269 */       synchronized (this.sync) {
/* 270 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 276 */       if (o == this) return true; 
/* 277 */       synchronized (this.sync) {
/* 278 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 283 */       synchronized (this.sync) {
/* 284 */         s.defaultWriteObject();
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
/*     */   public static <K> ReferenceCollection<K> synchronize(ReferenceCollection<K> c) {
/* 297 */     return new SynchronizedCollection<>(c);
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
/*     */   public static <K> ReferenceCollection<K> synchronize(ReferenceCollection<K> c, Object sync) {
/* 310 */     return new SynchronizedCollection<>(c, sync);
/*     */   }
/*     */   
/*     */   static class UnmodifiableCollection<K>
/*     */     implements ReferenceCollection<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceCollection<? extends K> collection;
/*     */     
/*     */     protected UnmodifiableCollection(ReferenceCollection<? extends K> c) {
/* 319 */       this.collection = Objects.<ReferenceCollection<? extends K>>requireNonNull(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K k) {
/* 324 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 329 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 334 */       return this.collection.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 339 */       return this.collection.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 344 */       return this.collection.contains(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 349 */       return ObjectIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 355 */       return (ObjectSpliterator)this.collection.spliterator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Stream<K> stream() {
/* 361 */       return (Stream)this.collection.stream();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Stream<K> parallelStream() {
/* 367 */       return (Stream)this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 372 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 377 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 382 */       return this.collection.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 387 */       this.collection.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 392 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 397 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 402 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 407 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(Predicate<? super K> filter) {
/* 412 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 417 */       return this.collection.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 422 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 427 */       if (o == this) return true; 
/* 428 */       return this.collection.equals(o);
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
/*     */   public static <K> ReferenceCollection<K> unmodifiable(ReferenceCollection<? extends K> c) {
/* 440 */     return new UnmodifiableCollection<>(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection<K>
/*     */     extends AbstractReferenceCollection<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectIterable<K> iterable;
/*     */     
/*     */     protected IterableCollection(ObjectIterable<K> iterable) {
/* 449 */       this.iterable = Objects.<ObjectIterable<K>>requireNonNull(iterable);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 454 */       long size = this.iterable.spliterator().getExactSizeIfKnown();
/* 455 */       if (size >= 0L) return (int)Math.min(2147483647L, size); 
/* 456 */       int c = 0;
/* 457 */       ObjectIterator<K> iterator = iterator();
/* 458 */       while (iterator.hasNext()) {
/* 459 */         iterator.next();
/* 460 */         c++;
/*     */       } 
/* 462 */       return c;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 467 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 472 */       return this.iterable.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 477 */       return this.iterable.spliterator();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceCollection<K> asCollection(ObjectIterable<K> iterable) {
/* 488 */     if (iterable instanceof ReferenceCollection) return (ReferenceCollection<K>)iterable;
/*     */     
/* 490 */     return new IterableCollection<>(iterable);
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
/*     */   static class SizeDecreasingSupplier<K, C extends ReferenceCollection<K>>
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
/* 516 */     final AtomicInteger suppliedCount = new AtomicInteger(0);
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
/* 543 */       this.expectedFinalSize = expectedFinalSize;
/* 544 */       this.builder = builder;
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
/* 555 */       int expectedNeededNextSize = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
/* 556 */       if (expectedNeededNextSize < 0)
/*     */       {
/* 558 */         expectedNeededNextSize = 8;
/*     */       }
/* 560 */       return this.builder.apply(expectedNeededNextSize);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */