/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
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
/*     */ public final class Object2FloatFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractObject2FloatFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public float getFloat(Object k) {
/*  44 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object k, float defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public float defaultReturnValue() {
/*  59 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
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
/*  78 */       return Object2FloatFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Object2FloatFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractObject2FloatFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final float value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, float value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 128 */       return Objects.equals(this.key, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloat(Object k) {
/* 133 */       return Objects.equals(this.key, k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object k, float defaultValue) {
/* 138 */       return Objects.equals(this.key, k) ? this.value : defaultValue;
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
/*     */   public static <K> Object2FloatFunction<K> singleton(K key, float value) {
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
/*     */   public static <K> Object2FloatFunction<K> singleton(K key, Float value) {
/* 179 */     return new Singleton<>(key, value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K>
/*     */     implements Object2FloatFunction<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2FloatFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Object2FloatFunction<K> f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Object2FloatFunction<K> f) {
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
/*     */     public Float apply(K key) {
/* 215 */       synchronized (this.sync) {
/* 216 */         return (Float)this.function.apply(key);
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
/*     */     public float defaultReturnValue() {
/* 229 */       synchronized (this.sync) {
/* 230 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
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
/*     */     public float put(K k, float v) {
/* 250 */       synchronized (this.sync) {
/* 251 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloat(Object k) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.getFloat(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object k, float defaultValue) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float removeFloat(Object k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.removeFloat(k);
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
/*     */     public Float put(K k, Float v) {
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
/*     */     public Float get(Object k) {
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
/*     */     public Float getOrDefault(Object k, Float defaultValue) {
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
/*     */     public Float remove(Object k) {
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
/*     */   public static <K> Object2FloatFunction<K> synchronize(Object2FloatFunction<K> f) {
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
/*     */   public static <K> Object2FloatFunction<K> synchronize(Object2FloatFunction<K> f, Object sync) {
/* 385 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractObject2FloatFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2FloatFunction<? extends K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Object2FloatFunction<? extends K> f) {
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
/*     */     public float defaultReturnValue() {
/* 405 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
/* 410 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 415 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public float put(K k, float v) {
/* 420 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloat(Object k) {
/* 425 */       return this.function.getFloat(k);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object k, float defaultValue) {
/* 431 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public float removeFloat(Object k) {
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
/*     */     public Float put(K k, Float v) {
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
/*     */     public Float get(Object k) {
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
/*     */     public Float getOrDefault(Object k, Float defaultValue) {
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
/*     */     public Float remove(Object k) {
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
/*     */   public static <K> Object2FloatFunction<K> unmodifiable(Object2FloatFunction<? extends K> f) {
/* 513 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Object2FloatFunction<K> {
/*     */     protected final Function<? super K, ? extends Float> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Float> function) {
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
/*     */     public float getFloat(Object key) {
/* 533 */       Float v = this.function.apply((K)key);
/* 534 */       if (v == null) return defaultReturnValue(); 
/* 535 */       return v.floatValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object key, float defaultValue) {
/* 541 */       Float v = this.function.apply((K)key);
/* 542 */       if (v == null) return defaultValue; 
/* 543 */       return v.floatValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float get(Object key) {
/* 550 */       return this.function.apply((K)key);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
/*     */       Float v;
/* 558 */       return ((v = this.function.apply((K)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float put(K key, Float value) {
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
/*     */   public static <K> Object2FloatFunction<K> primitive(Function<? super K, ? extends Float> f) {
/* 586 */     Objects.requireNonNull(f);
/* 587 */     if (f instanceof Object2FloatFunction) return (Object2FloatFunction)f; 
/* 588 */     if (f instanceof ToDoubleFunction) return key -> SafeMath.safeDoubleToFloat(((ToDoubleFunction<Object>)f).applyAsDouble(key)); 
/* 589 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2FloatFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */