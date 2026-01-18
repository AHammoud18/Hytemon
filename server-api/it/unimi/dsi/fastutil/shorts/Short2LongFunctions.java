/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntToLongFunction;
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
/*     */ public final class Short2LongFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractShort2LongFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public long get(short k) {
/*  44 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(short k, long defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short k) {
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
/*  78 */       return Short2LongFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Short2LongFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractShort2LongFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final short key;
/*     */ 
/*     */     
/*     */     protected final long value;
/*     */ 
/*     */     
/*     */     protected Singleton(short key, long value) {
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
/*     */     public long get(short k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(short k, long defaultValue) {
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
/*     */   public static Short2LongFunction singleton(short key, long value) {
/* 164 */     return new Singleton(key, value);
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
/*     */   public static Short2LongFunction singleton(Short key, Long value) {
/* 179 */     return new Singleton(key.shortValue(), value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction
/*     */     implements Short2LongFunction, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2LongFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Short2LongFunction f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Short2LongFunction f) {
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
/*     */     public long applyAsLong(int operand) {
/* 208 */       synchronized (this.sync) {
/* 209 */         return this.function.applyAsLong(operand);
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
/*     */     public Long apply(Short key) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return (Long)this.function.apply(key);
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
/*     */     public long defaultReturnValue() {
/* 235 */       synchronized (this.sync) {
/* 236 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
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
/*     */     public long put(short k, long v) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long get(short k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(short k, long defaultValue) {
/* 278 */       synchronized (this.sync) {
/* 279 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long remove(short k) {
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
/*     */     public Long put(Short k, Long v) {
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
/*     */     public Long get(Object k) {
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
/*     */     public Long getOrDefault(Object k, Long defaultValue) {
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
/*     */     public Long remove(Object k) {
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
/*     */   public static Short2LongFunction synchronize(Short2LongFunction f) {
/* 386 */     return new SynchronizedFunction(f);
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
/*     */   public static Short2LongFunction synchronize(Short2LongFunction f, Object sync) {
/* 399 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction
/*     */     extends AbstractShort2LongFunction implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2LongFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Short2LongFunction f) {
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
/*     */     public long defaultReturnValue() {
/* 419 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 424 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short k) {
/* 429 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long put(short k, long v) {
/* 434 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long get(short k) {
/* 439 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(short k, long defaultValue) {
/* 444 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public long remove(short k) {
/* 449 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 454 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long put(Short k, Long v) {
/* 465 */       throw new UnsupportedOperationException();
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
/* 476 */       return this.function.get(k);
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
/* 487 */       return this.function.getOrDefault(k, defaultValue);
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
/* 498 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 503 */       return this.function.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 508 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 513 */       return this.function.toString();
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
/*     */   public static Short2LongFunction unmodifiable(Short2LongFunction f) {
/* 525 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Short2LongFunction {
/*     */     protected final Function<? super Short, ? extends Long> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Short, ? extends Long> function) {
/* 533 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short key) {
/* 538 */       return (this.function.apply(Short.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 544 */       if (key == null) return false; 
/* 545 */       return (this.function.apply((Short)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public long get(short key) {
/* 550 */       Long v = this.function.apply(Short.valueOf(key));
/* 551 */       if (v == null) return defaultReturnValue(); 
/* 552 */       return v.longValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(short key, long defaultValue) {
/* 557 */       Long v = this.function.apply(Short.valueOf(key));
/* 558 */       if (v == null) return defaultValue; 
/* 559 */       return v.longValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object key) {
/* 565 */       if (key == null) return null; 
/* 566 */       return this.function.apply((Short)key);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 572 */       if (key == null) return defaultValue; 
/*     */       Long v;
/* 574 */       return ((v = this.function.apply((Short)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long put(Short key, Long value) {
/* 580 */       throw new UnsupportedOperationException();
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
/*     */   public static Short2LongFunction primitive(Function<? super Short, ? extends Long> f) {
/* 602 */     Objects.requireNonNull(f);
/* 603 */     if (f instanceof Short2LongFunction) return (Short2LongFunction)f; 
/* 604 */     if (f instanceof IntToLongFunction) { Objects.requireNonNull((IntToLongFunction)f); return (IntToLongFunction)f::applyAsLong; }
/* 605 */      return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2LongFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */