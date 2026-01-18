/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntPredicate;
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
/*     */ public final class IntSets
/*     */ {
/*     */   static final int ARRAY_SET_CUTOFF = 4;
/*     */   
/*     */   public static class EmptySet
/*     */     extends IntCollections.EmptyCollection
/*     */     implements IntSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(int ok) {
/*  48 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  53 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  59 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(int k) {
/*  65 */       return super.rem(k);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  69 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */   
/*  81 */   static final IntSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new IntArraySet(IntArrays.EMPTY_ARRAY));
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
/*     */   public static IntSet emptySet() {
/*  93 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractIntSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final int element;
/*     */ 
/*     */     
/*     */     protected Singleton(int element) {
/* 107 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int k) {
/* 112 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int k) {
/* 117 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntListIterator iterator() {
/* 122 */       return IntIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 127 */       return IntSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 132 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int[] toIntArray() {
/* 137 */       return new int[] { this.element };
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Integer> action) {
/* 143 */       action.accept(Integer.valueOf(this.element));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Integer> c) {
/* 148 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 153 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 158 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean removeIf(Predicate<? super Integer> filter) {
/* 164 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(IntConsumer action) {
/* 169 */       action.accept(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(IntCollection c) {
/* 174 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(IntCollection c) {
/* 179 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(IntCollection c) {
/* 184 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(IntPredicate filter) {
/* 189 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Object[] toArray() {
/* 195 */       return new Object[] { Integer.valueOf(this.element) };
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 200 */       return this;
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
/*     */   public static IntSet singleton(int element) {
/* 212 */     return new Singleton(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntSet singleton(Integer element) {
/* 223 */     return new Singleton(element.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends IntCollections.SynchronizedCollection implements IntSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(IntSet s, Object sync) {
/* 231 */       super(s, sync);
/*     */     }
/*     */     
/*     */     protected SynchronizedSet(IntSet s) {
/* 235 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int k) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(int k) {
/* 248 */       return super.rem(k);
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
/*     */   public static IntSet synchronize(IntSet s) {
/* 260 */     return new SynchronizedSet(s);
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
/*     */   public static IntSet synchronize(IntSet s, Object sync) {
/* 273 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends IntCollections.UnmodifiableCollection implements IntSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(IntSet s) {
/* 281 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int k) {
/* 286 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 291 */       if (o == this) return true; 
/* 292 */       return this.collection.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 297 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(int k) {
/* 303 */       return super.rem(k);
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
/*     */   public static IntSet unmodifiable(IntSet s) {
/* 315 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntSet fromTo(final int from, final int to) {
/* 326 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int x) {
/* 329 */           return (x >= from && x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 334 */           return IntIterators.fromTo(from, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 339 */           long size = to - from;
/* 340 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntSet from(final int from) {
/* 353 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int x) {
/* 356 */           return (x >= from);
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 361 */           return IntIterators.concat(new IntIterator[] { IntIterators.fromTo(this.val$from, 2147483647), 
/* 362 */                 IntSets.singleton(2147483647).iterator() });
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 367 */           long size = 2147483647L - from + 1L;
/* 368 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntSet to(final int to) {
/* 380 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int x) {
/* 383 */           return (x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 388 */           return IntIterators.fromTo(-2147483648, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 393 */           long size = to - -2147483648L;
/* 394 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */