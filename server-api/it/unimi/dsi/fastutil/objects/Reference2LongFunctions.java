/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToLongFunction;
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
/*     */ public final class Reference2LongFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractReference2LongFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public long getLong(Object k) {
/*  44 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(Object k, long defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public long defaultReturnValue() {
/*  59 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
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
/*  78 */       return Reference2LongFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Reference2LongFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractReference2LongFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final long value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, long value) {
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
/*     */     public long getLong(Object k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(Object k, long defaultValue) {
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
/*     */   public static <K> Reference2LongFunction<K> singleton(K key, long value) {
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
/*     */   public static <K> Reference2LongFunction<K> singleton(K key, Long value) {
/* 179 */     return new Singleton<>(key, value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K>
/*     */     implements Reference2LongFunction<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2LongFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Reference2LongFunction<K> f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Reference2LongFunction<K> f) {
/* 195 */       if (f == null) throw new NullPointerException(); 
/* 196 */       this.function = f;
/* 197 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public long applyAsLong(K operand) {
/* 202 */       synchronized (this.sync) {
/* 203 */         return this.function.applyAsLong(operand);
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
/*     */     public Long apply(K key) {
/* 215 */       synchronized (this.sync) {
/* 216 */         return (Long)this.function.apply(key);
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
/*     */     public long defaultReturnValue() {
/* 229 */       synchronized (this.sync) {
/* 230 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
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
/*     */     public long put(K k, long v) {
/* 250 */       synchronized (this.sync) {
/* 251 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLong(Object k) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.getLong(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(Object k, long defaultValue) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long removeLong(Object k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.removeLong(k);
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
/*     */     public Long put(K k, Long v) {
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
/*     */     public Long get(Object k) {
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
/*     */     public Long getOrDefault(Object k, Long defaultValue) {
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
/*     */     public Long remove(Object k) {
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
/*     */   public static <K> Reference2LongFunction<K> synchronize(Reference2LongFunction<K> f) {
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
/*     */   public static <K> Reference2LongFunction<K> synchronize(Reference2LongFunction<K> f, Object sync) {
/* 385 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractReference2LongFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2LongFunction<? extends K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Reference2LongFunction<? extends K> f) {
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
/*     */     public long defaultReturnValue() {
/* 405 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 410 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 415 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long put(K k, long v) {
/* 420 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLong(Object k) {
/* 425 */       return this.function.getLong(k);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getOrDefault(Object k, long defaultValue) {
/* 431 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public long removeLong(Object k) {
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
/*     */     public Long put(K k, Long v) {
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
/*     */     public Long get(Object k) {
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
/*     */     public Long getOrDefault(Object k, Long defaultValue) {
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
/*     */     public Long remove(Object k) {
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
/*     */   public static <K> Reference2LongFunction<K> unmodifiable(Reference2LongFunction<? extends K> f) {
/* 513 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Reference2LongFunction<K> {
/*     */     protected final Function<? super K, ? extends Long> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Long> function) {
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
/*     */     public long getLong(Object key) {
/* 533 */       Long v = this.function.apply((K)key);
/* 534 */       if (v == null) return defaultReturnValue(); 
/* 535 */       return v.longValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getOrDefault(Object key, long defaultValue) {
/* 541 */       Long v = this.function.apply((K)key);
/* 542 */       if (v == null) return defaultValue; 
/* 543 */       return v.longValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object key) {
/* 550 */       return this.function.apply((K)key);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/*     */       Long v;
/* 558 */       return ((v = this.function.apply((K)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long put(K key, Long value) {
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
/*     */   public static <K> Reference2LongFunction<K> primitive(Function<? super K, ? extends Long> f) {
/* 586 */     Objects.requireNonNull(f);
/* 587 */     if (f instanceof Reference2LongFunction) return (Reference2LongFunction)f; 
/* 588 */     if (f instanceof ToLongFunction) return key -> ((ToLongFunction<Object>)f).applyAsLong(key); 
/* 589 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2LongFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */