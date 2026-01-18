/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.DoublePredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.DoubleStream;
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
/*     */ public final class DoubleSets
/*     */ {
/*     */   static final int ARRAY_SET_CUTOFF = 4;
/*     */   
/*     */   public static class EmptySet
/*     */     extends DoubleCollections.EmptyCollection
/*     */     implements DoubleSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(double ok) {
/*  48 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  53 */       return DoubleSets.EMPTY_SET;
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
/*     */     public boolean rem(double k) {
/*  65 */       return super.rem(k);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  69 */       return DoubleSets.EMPTY_SET;
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
/*  81 */   static final DoubleSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new DoubleArraySet(DoubleArrays.EMPTY_ARRAY));
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
/*     */   public static DoubleSet emptySet() {
/*  93 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractDoubleSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final double element;
/*     */ 
/*     */     
/*     */     protected Singleton(double element) {
/* 107 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(double k) {
/* 112 */       return (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double k) {
/* 117 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleListIterator iterator() {
/* 122 */       return DoubleIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 127 */       return DoubleSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 132 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public double[] toDoubleArray() {
/* 137 */       return new double[] { this.element };
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Double> action) {
/* 143 */       action.accept(Double.valueOf(this.element));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Double> c) {
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
/*     */     public boolean removeIf(Predicate<? super Double> filter) {
/* 164 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 169 */       action.accept(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(DoubleCollection c) {
/* 174 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(DoubleCollection c) {
/* 179 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(DoubleCollection c) {
/* 184 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(DoublePredicate filter) {
/* 189 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Object[] toArray() {
/* 195 */       return new Object[] { Double.valueOf(this.element) };
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
/*     */   public static DoubleSet singleton(double element) {
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
/*     */   public static DoubleSet singleton(Double element) {
/* 223 */     return new Singleton(element.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends DoubleCollections.SynchronizedCollection implements DoubleSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(DoubleSet s, Object sync) {
/* 231 */       super(s, sync);
/*     */     }
/*     */     
/*     */     protected SynchronizedSet(DoubleSet s) {
/* 235 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double k) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(double k) {
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
/*     */   public static DoubleSet synchronize(DoubleSet s) {
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
/*     */   public static DoubleSet synchronize(DoubleSet s, Object sync) {
/* 273 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends DoubleCollections.UnmodifiableCollection implements DoubleSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(DoubleSet s) {
/* 281 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double k) {
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
/*     */     public boolean rem(double k) {
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
/*     */   public static DoubleSet unmodifiable(DoubleSet s) {
/* 315 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */