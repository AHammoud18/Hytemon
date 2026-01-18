/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterators;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public final class ShortSets
/*     */ {
/*     */   static final int ARRAY_SET_CUTOFF = 4;
/*     */   
/*     */   public static class EmptySet
/*     */     extends ShortCollections.EmptyCollection
/*     */     implements ShortSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(short ok) {
/*  52 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  57 */       return ShortSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  63 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(short k) {
/*  69 */       return super.rem(k);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  73 */       return ShortSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */   
/*  85 */   static final ShortSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new ShortArraySet(ShortArrays.EMPTY_ARRAY));
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
/*     */   public static ShortSet emptySet() {
/*  97 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractShortSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final short element;
/*     */ 
/*     */     
/*     */     protected Singleton(short element) {
/* 111 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(short k) {
/* 116 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortListIterator iterator() {
/* 126 */       return ShortIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 131 */       return ShortSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 136 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public short[] toShortArray() {
/* 141 */       return new short[] { this.element };
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Short> action) {
/* 147 */       action.accept(Short.valueOf(this.element));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Short> c) {
/* 152 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 157 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 162 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean removeIf(Predicate<? super Short> filter) {
/* 168 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 173 */       action.accept(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(ShortCollection c) {
/* 178 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(ShortCollection c) {
/* 183 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(ShortCollection c) {
/* 188 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(ShortPredicate filter) {
/* 193 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator intIterator() {
/* 198 */       return (IntIterator)IntIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 203 */       return IntSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Object[] toArray() {
/* 209 */       return new Object[] { Short.valueOf(this.element) };
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 214 */       return this;
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
/*     */   public static ShortSet singleton(short element) {
/* 226 */     return new Singleton(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortSet singleton(Short element) {
/* 237 */     return new Singleton(element.shortValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends ShortCollections.SynchronizedCollection implements ShortSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(ShortSet s, Object sync) {
/* 245 */       super(s, sync);
/*     */     }
/*     */     
/*     */     protected SynchronizedSet(ShortSet s) {
/* 249 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 254 */       synchronized (this.sync) {
/* 255 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(short k) {
/* 262 */       return super.rem(k);
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
/*     */   public static ShortSet synchronize(ShortSet s) {
/* 274 */     return new SynchronizedSet(s);
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
/*     */   public static ShortSet synchronize(ShortSet s, Object sync) {
/* 287 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends ShortCollections.UnmodifiableCollection implements ShortSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(ShortSet s) {
/* 295 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 300 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 305 */       if (o == this) return true; 
/* 306 */       return this.collection.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 311 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(short k) {
/* 317 */       return super.rem(k);
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
/*     */   public static ShortSet unmodifiable(ShortSet s) {
/* 329 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortSet fromTo(final short from, final short to) {
/* 340 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short x) {
/* 343 */           return (x >= from && x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 348 */           return ShortIterators.fromTo(from, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 353 */           long size = to - from;
/* 354 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
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
/*     */   public static ShortSet from(final short from) {
/* 367 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short x) {
/* 370 */           return (x >= from);
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 375 */           return ShortIterators.concat(new ShortIterator[] { ShortIterators.fromTo(this.val$from, '翿'), 
/* 376 */                 ShortSets.singleton('翿').iterator() });
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 381 */           long size = 32767L - from + 1L;
/* 382 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
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
/*     */   public static ShortSet to(final short to) {
/* 394 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short x) {
/* 397 */           return (x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 402 */           return ShortIterators.fromTo(-32768, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 407 */           long size = to - -32768L;
/* 408 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */