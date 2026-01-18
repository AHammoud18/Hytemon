/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
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
/*     */ 
/*     */ public final class BooleanCollections
/*     */ {
/*     */   public static abstract class EmptyCollection
/*     */     extends AbstractBooleanCollection
/*     */   {
/*     */     public boolean contains(boolean k) {
/*  51 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/*  56 */       return ObjectArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/*  61 */       if (array.length > 0) array[0] = null; 
/*  62 */       return array;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BooleanBidirectionalIterator iterator() {
/*  68 */       return BooleanIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BooleanSpliterator spliterator() {
/*  74 */       return BooleanSpliterators.EMPTY_SPLITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  79 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  88 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  93 */       if (o == this) return true; 
/*  94 */       if (!(o instanceof Collection)) return false; 
/*  95 */       return ((Collection)o).isEmpty();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Boolean> action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 105 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Boolean> c) {
/* 110 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 115 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 120 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean removeIf(Predicate<? super Boolean> filter) {
/* 126 */       Objects.requireNonNull(filter);
/* 127 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toBooleanArray() {
/* 132 */       return BooleanArrays.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean[] toBooleanArray(boolean[] a) {
/* 141 */       return a;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BooleanConsumer action) {}
/*     */ 
/*     */     
/*     */     public boolean containsAll(BooleanCollection c) {
/* 150 */       return c.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(BooleanCollection c) {
/* 155 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(BooleanCollection c) {
/* 160 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(BooleanCollection c) {
/* 165 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(BooleanPredicate filter) {
/* 170 */       Objects.requireNonNull(filter);
/* 171 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class SynchronizedCollection
/*     */     implements BooleanCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanCollection collection;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedCollection(BooleanCollection c, Object sync) {
/* 182 */       this.collection = Objects.<BooleanCollection>requireNonNull(c);
/* 183 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedCollection(BooleanCollection c) {
/* 187 */       this.collection = Objects.<BooleanCollection>requireNonNull(c);
/* 188 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(boolean k) {
/* 193 */       synchronized (this.sync) {
/* 194 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(boolean k) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(boolean k) {
/* 207 */       synchronized (this.sync) {
/* 208 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 214 */       synchronized (this.sync) {
/* 215 */         return this.collection.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 221 */       synchronized (this.sync) {
/* 222 */         return this.collection.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toBooleanArray() {
/* 228 */       synchronized (this.sync) {
/* 229 */         return this.collection.toBooleanArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 235 */       synchronized (this.sync) {
/* 236 */         return this.collection.toArray();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean[] toBooleanArray(boolean[] a) {
/* 246 */       return toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toArray(boolean[] a) {
/* 251 */       synchronized (this.sync) {
/* 252 */         return this.collection.toArray(a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(BooleanCollection c) {
/* 258 */       synchronized (this.sync) {
/* 259 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(BooleanCollection c) {
/* 265 */       synchronized (this.sync) {
/* 266 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(BooleanCollection c) {
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(BooleanCollection c) {
/* 279 */       synchronized (this.sync) {
/* 280 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Boolean k) {
/* 287 */       synchronized (this.sync) {
/* 288 */         return this.collection.add(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 295 */       synchronized (this.sync) {
/* 296 */         return this.collection.contains(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 303 */       synchronized (this.sync) {
/* 304 */         return this.collection.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 310 */       synchronized (this.sync) {
/* 311 */         return (T[])this.collection.toArray((Object[])a);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanIterator iterator() {
/* 317 */       return this.collection.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanSpliterator spliterator() {
/* 322 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Boolean> stream() {
/* 327 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Boolean> parallelStream() {
/* 332 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BooleanConsumer action) {
/* 337 */       synchronized (this.sync) {
/* 338 */         this.collection.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Boolean> c) {
/* 344 */       synchronized (this.sync) {
/* 345 */         return this.collection.addAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 351 */       synchronized (this.sync) {
/* 352 */         return this.collection.containsAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 358 */       synchronized (this.sync) {
/* 359 */         return this.collection.removeAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 365 */       synchronized (this.sync) {
/* 366 */         return this.collection.retainAll(c);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(BooleanPredicate filter) {
/* 372 */       synchronized (this.sync) {
/* 373 */         return this.collection.removeIf(filter);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 379 */       synchronized (this.sync) {
/* 380 */         this.collection.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 386 */       synchronized (this.sync) {
/* 387 */         return this.collection.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 393 */       synchronized (this.sync) {
/* 394 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 400 */       if (o == this) return true; 
/* 401 */       synchronized (this.sync) {
/* 402 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 407 */       synchronized (this.sync) {
/* 408 */         s.defaultWriteObject();
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
/*     */   public static BooleanCollection synchronize(BooleanCollection c) {
/* 421 */     return new SynchronizedCollection(c);
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
/*     */   public static BooleanCollection synchronize(BooleanCollection c, Object sync) {
/* 434 */     return new SynchronizedCollection(c, sync);
/*     */   }
/*     */   
/*     */   static class UnmodifiableCollection
/*     */     implements BooleanCollection, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanCollection collection;
/*     */     
/*     */     protected UnmodifiableCollection(BooleanCollection c) {
/* 443 */       this.collection = Objects.<BooleanCollection>requireNonNull(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(boolean k) {
/* 448 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(boolean k) {
/* 453 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 458 */       return this.collection.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 463 */       return this.collection.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(boolean o) {
/* 468 */       return this.collection.contains(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanIterator iterator() {
/* 473 */       return BooleanIterators.unmodifiable(this.collection.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanSpliterator spliterator() {
/* 478 */       return this.collection.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Boolean> stream() {
/* 483 */       return this.collection.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Boolean> parallelStream() {
/* 488 */       return this.collection.parallelStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 493 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 498 */       return (T[])this.collection.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 503 */       return this.collection.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BooleanConsumer action) {
/* 508 */       this.collection.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 513 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Boolean> c) {
/* 518 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 523 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 528 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(BooleanPredicate filter) {
/* 533 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean add(Boolean k) {
/* 539 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean contains(Object k) {
/* 545 */       return this.collection.contains(k);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object k) {
/* 551 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toBooleanArray() {
/* 556 */       return this.collection.toBooleanArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean[] toBooleanArray(boolean[] a) {
/* 565 */       return toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toArray(boolean[] a) {
/* 570 */       return this.collection.toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(BooleanCollection c) {
/* 575 */       return this.collection.containsAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(BooleanCollection c) {
/* 580 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(BooleanCollection c) {
/* 585 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(BooleanCollection c) {
/* 590 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 595 */       return this.collection.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 600 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 605 */       if (o == this) return true; 
/* 606 */       return this.collection.equals(o);
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
/*     */   public static BooleanCollection unmodifiable(BooleanCollection c) {
/* 618 */     return new UnmodifiableCollection(c);
/*     */   }
/*     */   
/*     */   public static class IterableCollection
/*     */     extends AbstractBooleanCollection implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanIterable iterable;
/*     */     
/*     */     protected IterableCollection(BooleanIterable iterable) {
/* 627 */       this.iterable = Objects.<BooleanIterable>requireNonNull(iterable);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 632 */       long size = this.iterable.spliterator().getExactSizeIfKnown();
/* 633 */       if (size >= 0L) return (int)Math.min(2147483647L, size); 
/* 634 */       int c = 0;
/* 635 */       BooleanIterator iterator = iterator();
/* 636 */       while (iterator.hasNext()) {
/* 637 */         iterator.nextBoolean();
/* 638 */         c++;
/*     */       } 
/* 640 */       return c;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 645 */       return !this.iterable.iterator().hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanIterator iterator() {
/* 650 */       return this.iterable.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanSpliterator spliterator() {
/* 655 */       return this.iterable.spliterator();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanCollection asCollection(BooleanIterable iterable) {
/* 666 */     if (iterable instanceof BooleanCollection) return (BooleanCollection)iterable;
/*     */     
/* 668 */     return new IterableCollection(iterable);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */