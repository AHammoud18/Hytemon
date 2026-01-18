/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public final class ByteSets
/*     */ {
/*     */   static final int ARRAY_SET_CUTOFF = 4;
/*     */   
/*     */   public static class EmptySet
/*     */     extends ByteCollections.EmptyCollection
/*     */     implements ByteSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(byte ok) {
/*  52 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  57 */       return ByteSets.EMPTY_SET;
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
/*     */     public boolean rem(byte k) {
/*  69 */       return super.rem(k);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  73 */       return ByteSets.EMPTY_SET;
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
/*  85 */   static final ByteSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new ByteArraySet(ByteArrays.EMPTY_ARRAY));
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
/*     */   public static ByteSet emptySet() {
/*  97 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractByteSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final byte element;
/*     */ 
/*     */     
/*     */     protected Singleton(byte element) {
/* 111 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(byte k) {
/* 116 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte k) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteListIterator iterator() {
/* 126 */       return ByteIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 131 */       return ByteSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 136 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] toByteArray() {
/* 141 */       return new byte[] { this.element };
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Byte> action) {
/* 147 */       action.accept(Byte.valueOf(this.element));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Byte> c) {
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
/*     */     public boolean removeIf(Predicate<? super Byte> filter) {
/* 168 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 173 */       action.accept(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(ByteCollection c) {
/* 178 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(ByteCollection c) {
/* 183 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(ByteCollection c) {
/* 188 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(BytePredicate filter) {
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
/* 209 */       return new Object[] { Byte.valueOf(this.element) };
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
/*     */   public static ByteSet singleton(byte element) {
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
/*     */   public static ByteSet singleton(Byte element) {
/* 237 */     return new Singleton(element.byteValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends ByteCollections.SynchronizedCollection implements ByteSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(ByteSet s, Object sync) {
/* 245 */       super(s, sync);
/*     */     }
/*     */     
/*     */     protected SynchronizedSet(ByteSet s) {
/* 249 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte k) {
/* 254 */       synchronized (this.sync) {
/* 255 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(byte k) {
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
/*     */   public static ByteSet synchronize(ByteSet s) {
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
/*     */   public static ByteSet synchronize(ByteSet s, Object sync) {
/* 287 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends ByteCollections.UnmodifiableCollection implements ByteSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(ByteSet s) {
/* 295 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte k) {
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
/*     */     public boolean rem(byte k) {
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
/*     */   public static ByteSet unmodifiable(ByteSet s) {
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
/*     */   public static ByteSet fromTo(final byte from, final byte to) {
/* 340 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte x) {
/* 343 */           return (x >= from && x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 348 */           return ByteIterators.fromTo(from, to);
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
/*     */   public static ByteSet from(final byte from) {
/* 367 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte x) {
/* 370 */           return (x >= from);
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 375 */           return ByteIterators.concat(new ByteIterator[] { ByteIterators.fromTo(this.val$from, 127), 
/* 376 */                 ByteSets.singleton(127).iterator() });
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 381 */           long size = 127L - from + 1L;
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
/*     */   public static ByteSet to(final byte to) {
/* 394 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte x) {
/* 397 */           return (x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 402 */           return ByteIterators.fromTo(-128, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 407 */           long size = to - -128L;
/* 408 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */