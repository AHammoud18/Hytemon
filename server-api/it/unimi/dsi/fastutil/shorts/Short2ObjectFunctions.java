/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
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
/*     */ public final class Short2ObjectFunctions
/*     */ {
/*     */   public static class EmptyFunction<V>
/*     */     extends AbstractShort2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(short k) {
/*  44 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(short k, V defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V defaultReturnValue() {
/*  59 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
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
/*  78 */       return Short2ObjectFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Short2ObjectFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends AbstractShort2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final short key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */     
/*     */     protected Singleton(short key, V value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short k) {
/* 128 */       return (this.key == k);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(short k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(short k, V defaultValue) {
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
/*     */   public static <V> Short2ObjectFunction<V> singleton(short key, V value) {
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
/*     */   public static <V> Short2ObjectFunction<V> singleton(Short key, V value) {
/* 179 */     return new Singleton<>(key.shortValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<V>
/*     */     implements Short2ObjectFunction<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ObjectFunction<V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Short2ObjectFunction<V> f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Short2ObjectFunction<V> f) {
/* 195 */       if (f == null) throw new NullPointerException(); 
/* 196 */       this.function = f;
/* 197 */       this.sync = this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(int operand) {
/* 208 */       synchronized (this.sync) {
/* 209 */         return this.function.apply(operand);
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
/*     */     public V apply(Short key) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 228 */       synchronized (this.sync) {
/* 229 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V defaultReturnValue() {
/* 235 */       synchronized (this.sync) {
/* 236 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 242 */       synchronized (this.sync) {
/* 243 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short k) {
/* 249 */       synchronized (this.sync) {
/* 250 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(short k, V v) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(short k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(short k, V defaultValue) {
/* 278 */       synchronized (this.sync) {
/* 279 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(short k) {
/* 285 */       synchronized (this.sync) {
/* 286 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 292 */       synchronized (this.sync) {
/* 293 */         this.function.clear();
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
/*     */     public V put(Short k, V v) {
/* 305 */       synchronized (this.sync) {
/* 306 */         return this.function.put(k, v);
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
/*     */     public V get(Object k) {
/* 318 */       synchronized (this.sync) {
/* 319 */         return this.function.get(k);
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
/*     */     public V getOrDefault(Object k, V defaultValue) {
/* 331 */       synchronized (this.sync) {
/* 332 */         return this.function.getOrDefault(k, defaultValue);
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
/*     */     public V remove(Object k) {
/* 344 */       synchronized (this.sync) {
/* 345 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 351 */       synchronized (this.sync) {
/* 352 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 358 */       if (o == this) return true; 
/* 359 */       synchronized (this.sync) {
/* 360 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 366 */       synchronized (this.sync) {
/* 367 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 372 */       synchronized (this.sync) {
/* 373 */         s.defaultWriteObject();
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
/*     */   public static <V> Short2ObjectFunction<V> synchronize(Short2ObjectFunction<V> f) {
/* 386 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <V> Short2ObjectFunction<V> synchronize(Short2ObjectFunction<V> f, Object sync) {
/* 399 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<V>
/*     */     extends AbstractShort2ObjectFunction<V> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ObjectFunction<? extends V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Short2ObjectFunction<? extends V> f) {
/* 408 */       if (f == null) throw new NullPointerException(); 
/* 409 */       this.function = f;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 414 */       return this.function.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public V defaultReturnValue() {
/* 419 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 424 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short k) {
/* 429 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(short k, V v) {
/* 434 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(short k) {
/* 439 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(short k, V defaultValue) {
/* 445 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(short k) {
/* 450 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 455 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Short k, V v) {
/* 466 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object k) {
/* 477 */       return this.function.get(k);
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
/*     */     public V getOrDefault(Object k, V defaultValue) {
/* 489 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V remove(Object k) {
/* 500 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 505 */       return this.function.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 510 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 515 */       return this.function.toString();
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
/*     */   public static <V> Short2ObjectFunction<V> unmodifiable(Short2ObjectFunction<? extends V> f) {
/* 527 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<V>
/*     */     implements Short2ObjectFunction<V> {
/*     */     protected final Function<? super Short, ? extends V> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Short, ? extends V> function) {
/* 535 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short key) {
/* 540 */       return (this.function.apply(Short.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 546 */       if (key == null) return false; 
/* 547 */       return (this.function.apply((Short)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(short key) {
/* 552 */       V v = this.function.apply(Short.valueOf(key));
/* 553 */       if (v == null) return null; 
/* 554 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(short key, V defaultValue) {
/* 559 */       V v = this.function.apply(Short.valueOf(key));
/* 560 */       if (v == null) return defaultValue; 
/* 561 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object key) {
/* 567 */       if (key == null) return null; 
/* 568 */       return this.function.apply((Short)key);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 574 */       if (key == null) return defaultValue; 
/*     */       V v;
/* 576 */       return ((v = this.function.apply((Short)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Short key, V value) {
/* 582 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Short2ObjectFunction<V> primitive(Function<? super Short, ? extends V> f) {
/* 604 */     Objects.requireNonNull(f);
/* 605 */     if (f instanceof Short2ObjectFunction) return (Short2ObjectFunction)f; 
/* 606 */     if (f instanceof IntFunction) { Objects.requireNonNull((IntFunction)f); return (IntFunction)f::apply; }
/* 607 */      return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ObjectFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */