/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToDoubleFunction;
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
/*     */ public final class Reference2DoubleFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractReference2DoubleFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public double getDouble(Object k) {
/*  44 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object k, double defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public double defaultReturnValue() {
/*  59 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(double defRetValue) {
/*  64 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  69 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  78 */       return Reference2DoubleFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  83 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  88 */       if (!(o instanceof Function)) return false; 
/*  89 */       return (((Function)o).size() == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  94 */       return "{}";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  98 */       return Reference2DoubleFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractReference2DoubleFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final double value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, double value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 128 */       return (this.key == k);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDouble(Object k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object k, double defaultValue) {
/* 138 */       return (this.key == k) ? this.value : defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 143 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 148 */       return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Reference2DoubleFunction<K> singleton(K key, double value) {
/* 164 */     return new Singleton<>(key, value);
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
/*     */   
/*     */   public static <K> Reference2DoubleFunction<K> singleton(K key, Double value) {
/* 179 */     return new Singleton<>(key, value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K>
/*     */     implements Reference2DoubleFunction<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2DoubleFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Reference2DoubleFunction<K> f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Reference2DoubleFunction<K> f) {
/* 195 */       if (f == null) throw new NullPointerException(); 
/* 196 */       this.function = f;
/* 197 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public double applyAsDouble(K operand) {
/* 202 */       synchronized (this.sync) {
/* 203 */         return this.function.applyAsDouble(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double apply(K key) {
/* 215 */       synchronized (this.sync) {
/* 216 */         return (Double)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 222 */       synchronized (this.sync) {
/* 223 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double defaultReturnValue() {
/* 229 */       synchronized (this.sync) {
/* 230 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(double defRetValue) {
/* 236 */       synchronized (this.sync) {
/* 237 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double put(K k, double v) {
/* 250 */       synchronized (this.sync) {
/* 251 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDouble(Object k) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.getDouble(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object k, double defaultValue) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double removeDouble(Object k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.removeDouble(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 278 */       synchronized (this.sync) {
/* 279 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double put(K k, Double v) {
/* 291 */       synchronized (this.sync) {
/* 292 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double get(Object k) {
/* 304 */       synchronized (this.sync) {
/* 305 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object k, Double defaultValue) {
/* 317 */       synchronized (this.sync) {
/* 318 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double remove(Object k) {
/* 330 */       synchronized (this.sync) {
/* 331 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 337 */       synchronized (this.sync) {
/* 338 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 344 */       if (o == this) return true; 
/* 345 */       synchronized (this.sync) {
/* 346 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 352 */       synchronized (this.sync) {
/* 353 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 358 */       synchronized (this.sync) {
/* 359 */         s.defaultWriteObject();
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
/*     */   public static <K> Reference2DoubleFunction<K> synchronize(Reference2DoubleFunction<K> f) {
/* 372 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <K> Reference2DoubleFunction<K> synchronize(Reference2DoubleFunction<K> f, Object sync) {
/* 385 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractReference2DoubleFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2DoubleFunction<? extends K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Reference2DoubleFunction<? extends K> f) {
/* 394 */       if (f == null) throw new NullPointerException(); 
/* 395 */       this.function = f;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 400 */       return this.function.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public double defaultReturnValue() {
/* 405 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(double defRetValue) {
/* 410 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 415 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public double put(K k, double v) {
/* 420 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDouble(Object k) {
/* 425 */       return this.function.getDouble(k);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object k, double defaultValue) {
/* 431 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public double removeDouble(Object k) {
/* 436 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 441 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double put(K k, Double v) {
/* 452 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double get(Object k) {
/* 463 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object k, Double defaultValue) {
/* 475 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double remove(Object k) {
/* 486 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 491 */       return this.function.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 496 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 501 */       return this.function.toString();
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
/*     */   public static <K> Reference2DoubleFunction<K> unmodifiable(Reference2DoubleFunction<? extends K> f) {
/* 513 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Reference2DoubleFunction<K> {
/*     */     protected final Function<? super K, ? extends Double> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Double> function) {
/* 521 */       this.function = function;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 527 */       return (this.function.apply((K)key) != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double getDouble(Object key) {
/* 533 */       Double v = this.function.apply((K)key);
/* 534 */       if (v == null) return defaultReturnValue(); 
/* 535 */       return v.doubleValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object key, double defaultValue) {
/* 541 */       Double v = this.function.apply((K)key);
/* 542 */       if (v == null) return defaultValue; 
/* 543 */       return v.doubleValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double get(Object key) {
/* 550 */       return this.function.apply((K)key);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
/*     */       Double v;
/* 558 */       return ((v = this.function.apply((K)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double put(K key, Double value) {
/* 564 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Reference2DoubleFunction<K> primitive(Function<? super K, ? extends Double> f) {
/* 586 */     Objects.requireNonNull(f);
/* 587 */     if (f instanceof Reference2DoubleFunction) return (Reference2DoubleFunction)f; 
/* 588 */     if (f instanceof ToDoubleFunction) return key -> ((ToDoubleFunction<Object>)f).applyAsDouble(key); 
/* 589 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */