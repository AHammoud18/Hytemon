/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2BooleanFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractReference2BooleanFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean getBoolean(Object k) {
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object k, boolean defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean defaultReturnValue() {
/*  59 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
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
/*  78 */       return Reference2BooleanFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Reference2BooleanFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractReference2BooleanFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final boolean value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, boolean value) {
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
/*     */     public boolean getBoolean(Object k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object k, boolean defaultValue) {
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
/*     */   public static <K> Reference2BooleanFunction<K> singleton(K key, boolean value) {
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
/*     */   public static <K> Reference2BooleanFunction<K> singleton(K key, Boolean value) {
/* 179 */     return new Singleton<>(key, value.booleanValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K>
/*     */     implements Reference2BooleanFunction<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2BooleanFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Reference2BooleanFunction<K> f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Reference2BooleanFunction<K> f) {
/* 195 */       if (f == null) throw new NullPointerException(); 
/* 196 */       this.function = f;
/* 197 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(K operand) {
/* 202 */       synchronized (this.sync) {
/* 203 */         return this.function.test(operand);
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
/*     */     public Boolean apply(K key) {
/* 215 */       synchronized (this.sync) {
/* 216 */         return (Boolean)this.function.apply(key);
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
/*     */     public boolean defaultReturnValue() {
/* 229 */       synchronized (this.sync) {
/* 230 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
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
/*     */     public boolean put(K k, boolean v) {
/* 250 */       synchronized (this.sync) {
/* 251 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean(Object k) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.getBoolean(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object k, boolean defaultValue) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeBoolean(Object k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.removeBoolean(k);
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
/*     */     public Boolean put(K k, Boolean v) {
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
/*     */     public Boolean get(Object k) {
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
/*     */     public Boolean getOrDefault(Object k, Boolean defaultValue) {
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
/*     */     public Boolean remove(Object k) {
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
/*     */   public static <K> Reference2BooleanFunction<K> synchronize(Reference2BooleanFunction<K> f) {
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
/*     */   public static <K> Reference2BooleanFunction<K> synchronize(Reference2BooleanFunction<K> f, Object sync) {
/* 385 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractReference2BooleanFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2BooleanFunction<? extends K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Reference2BooleanFunction<? extends K> f) {
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
/*     */     public boolean defaultReturnValue() {
/* 405 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
/* 410 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 415 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean put(K k, boolean v) {
/* 420 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean(Object k) {
/* 425 */       return this.function.getBoolean(k);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object k, boolean defaultValue) {
/* 431 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeBoolean(Object k) {
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
/*     */     public Boolean put(K k, Boolean v) {
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
/*     */     public Boolean get(Object k) {
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
/*     */     public Boolean getOrDefault(Object k, Boolean defaultValue) {
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
/*     */     public Boolean remove(Object k) {
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
/*     */   public static <K> Reference2BooleanFunction<K> unmodifiable(Reference2BooleanFunction<? extends K> f) {
/* 513 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Reference2BooleanFunction<K> {
/*     */     protected final Function<? super K, ? extends Boolean> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Boolean> function) {
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
/*     */     public boolean getBoolean(Object key) {
/* 533 */       Boolean v = this.function.apply((K)key);
/* 534 */       if (v == null) return defaultReturnValue(); 
/* 535 */       return v.booleanValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object key, boolean defaultValue) {
/* 541 */       Boolean v = this.function.apply((K)key);
/* 542 */       if (v == null) return defaultValue; 
/* 543 */       return v.booleanValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean get(Object key) {
/* 550 */       return this.function.apply((K)key);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean getOrDefault(Object key, Boolean defaultValue) {
/*     */       Boolean v;
/* 558 */       return ((v = this.function.apply((K)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean put(K key, Boolean value) {
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
/*     */   public static <K> Reference2BooleanFunction<K> primitive(Function<? super K, ? extends Boolean> f) {
/* 586 */     Objects.requireNonNull(f);
/* 587 */     if (f instanceof Reference2BooleanFunction) return (Reference2BooleanFunction)f; 
/* 588 */     if (f instanceof Predicate) return key -> ((Predicate<Object>)f).test(key); 
/* 589 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */