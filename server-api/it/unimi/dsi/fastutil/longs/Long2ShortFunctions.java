/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongToIntFunction;
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
/*     */ public final class Long2ShortFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractLong2ShortFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public short get(long k) {
/*  44 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOrDefault(long k, short defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(long k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public short defaultReturnValue() {
/*  59 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(short defRetValue) {
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
/*  78 */       return Long2ShortFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Long2ShortFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractLong2ShortFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final long key;
/*     */ 
/*     */     
/*     */     protected final short value;
/*     */ 
/*     */     
/*     */     protected Singleton(long key, short value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(long k) {
/* 128 */       return (this.key == k);
/*     */     }
/*     */ 
/*     */     
/*     */     public short get(long k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOrDefault(long k, short defaultValue) {
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
/*     */   public static Long2ShortFunction singleton(long key, short value) {
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
/*     */   public static Long2ShortFunction singleton(Long key, Short value) {
/* 179 */     return new Singleton(key.longValue(), value.shortValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction
/*     */     implements Long2ShortFunction, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ShortFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Long2ShortFunction f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Long2ShortFunction f) {
/* 195 */       if (f == null) throw new NullPointerException(); 
/* 196 */       this.function = f;
/* 197 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public int applyAsInt(long operand) {
/* 202 */       synchronized (this.sync) {
/* 203 */         return this.function.applyAsInt(operand);
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
/*     */     public Short apply(Long key) {
/* 215 */       synchronized (this.sync) {
/* 216 */         return (Short)this.function.apply(key);
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
/*     */     public short defaultReturnValue() {
/* 229 */       synchronized (this.sync) {
/* 230 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(short defRetValue) {
/* 236 */       synchronized (this.sync) {
/* 237 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(long k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 251 */       synchronized (this.sync) {
/* 252 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short put(long k, short v) {
/* 258 */       synchronized (this.sync) {
/* 259 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short get(long k) {
/* 265 */       synchronized (this.sync) {
/* 266 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOrDefault(long k, short defaultValue) {
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short remove(long k) {
/* 279 */       synchronized (this.sync) {
/* 280 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 286 */       synchronized (this.sync) {
/* 287 */         this.function.clear();
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
/*     */     public Short put(Long k, Short v) {
/* 299 */       synchronized (this.sync) {
/* 300 */         return this.function.put(k, v);
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
/*     */     public Short get(Object k) {
/* 312 */       synchronized (this.sync) {
/* 313 */         return this.function.get(k);
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
/*     */     public Short getOrDefault(Object k, Short defaultValue) {
/* 325 */       synchronized (this.sync) {
/* 326 */         return this.function.getOrDefault(k, defaultValue);
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
/*     */     public Short remove(Object k) {
/* 338 */       synchronized (this.sync) {
/* 339 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 345 */       synchronized (this.sync) {
/* 346 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 352 */       if (o == this) return true; 
/* 353 */       synchronized (this.sync) {
/* 354 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 360 */       synchronized (this.sync) {
/* 361 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 366 */       synchronized (this.sync) {
/* 367 */         s.defaultWriteObject();
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
/*     */   public static Long2ShortFunction synchronize(Long2ShortFunction f) {
/* 380 */     return new SynchronizedFunction(f);
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
/*     */   public static Long2ShortFunction synchronize(Long2ShortFunction f, Object sync) {
/* 393 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction
/*     */     extends AbstractLong2ShortFunction implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ShortFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Long2ShortFunction f) {
/* 402 */       if (f == null) throw new NullPointerException(); 
/* 403 */       this.function = f;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 408 */       return this.function.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public short defaultReturnValue() {
/* 413 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(short defRetValue) {
/* 418 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(long k) {
/* 423 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public short put(long k, short v) {
/* 428 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short get(long k) {
/* 433 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOrDefault(long k, short defaultValue) {
/* 438 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public short remove(long k) {
/* 443 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 448 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short put(Long k, Short v) {
/* 459 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short get(Object k) {
/* 470 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short getOrDefault(Object k, Short defaultValue) {
/* 481 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short remove(Object k) {
/* 492 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 497 */       return this.function.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 502 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 507 */       return this.function.toString();
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
/*     */   public static Long2ShortFunction unmodifiable(Long2ShortFunction f) {
/* 519 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Long2ShortFunction {
/*     */     protected final Function<? super Long, ? extends Short> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Long, ? extends Short> function) {
/* 527 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(long key) {
/* 532 */       return (this.function.apply(Long.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 538 */       if (key == null) return false; 
/* 539 */       return (this.function.apply((Long)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public short get(long key) {
/* 544 */       Short v = this.function.apply(Long.valueOf(key));
/* 545 */       if (v == null) return defaultReturnValue(); 
/* 546 */       return v.shortValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOrDefault(long key, short defaultValue) {
/* 551 */       Short v = this.function.apply(Long.valueOf(key));
/* 552 */       if (v == null) return defaultValue; 
/* 553 */       return v.shortValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short get(Object key) {
/* 559 */       if (key == null) return null; 
/* 560 */       return this.function.apply((Long)key);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short getOrDefault(Object key, Short defaultValue) {
/* 566 */       if (key == null) return defaultValue; 
/*     */       Short v;
/* 568 */       return ((v = this.function.apply((Long)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short put(Long key, Short value) {
/* 574 */       throw new UnsupportedOperationException();
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
/*     */   public static Long2ShortFunction primitive(Function<? super Long, ? extends Short> f) {
/* 596 */     Objects.requireNonNull(f);
/* 597 */     if (f instanceof Long2ShortFunction) return (Long2ShortFunction)f; 
/* 598 */     if (f instanceof LongToIntFunction) return key -> SafeMath.safeIntToShort(((LongToIntFunction)f).applyAsInt(key)); 
/* 599 */     return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2ShortFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */