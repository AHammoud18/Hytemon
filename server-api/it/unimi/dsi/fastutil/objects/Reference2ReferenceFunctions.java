/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2ReferenceFunctions
/*     */ {
/*     */   public static class EmptyFunction<K, V>
/*     */     extends AbstractReference2ReferenceFunction<K, V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(Object k) {
/*  44 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object k, V defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
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
/*  78 */       return Reference2ReferenceFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Reference2ReferenceFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends AbstractReference2ReferenceFunction<K, V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value) {
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
/*     */     public V get(Object k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object k, V defaultValue) {
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
/*     */   public static <K, V> Reference2ReferenceFunction<K, V> singleton(K key, V value) {
/* 164 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K, V>
/*     */     implements Reference2ReferenceFunction<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ReferenceFunction<K, V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Reference2ReferenceFunction<K, V> f, Object sync) {
/* 174 */       if (f == null) throw new NullPointerException(); 
/* 175 */       this.function = f;
/* 176 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Reference2ReferenceFunction<K, V> f) {
/* 180 */       if (f == null) throw new NullPointerException(); 
/* 181 */       this.function = f;
/* 182 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public V apply(K key) {
/* 187 */       synchronized (this.sync) {
/* 188 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 194 */       synchronized (this.sync) {
/* 195 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V defaultReturnValue() {
/* 201 */       synchronized (this.sync) {
/* 202 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 208 */       synchronized (this.sync) {
/* 209 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 215 */       synchronized (this.sync) {
/* 216 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(K k, V v) {
/* 222 */       synchronized (this.sync) {
/* 223 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(Object k) {
/* 229 */       synchronized (this.sync) {
/* 230 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object k, V defaultValue) {
/* 236 */       synchronized (this.sync) {
/* 237 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(Object k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 250 */       synchronized (this.sync) {
/* 251 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 264 */       if (o == this) return true; 
/* 265 */       synchronized (this.sync) {
/* 266 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 278 */       synchronized (this.sync) {
/* 279 */         s.defaultWriteObject();
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
/*     */   public static <K, V> Reference2ReferenceFunction<K, V> synchronize(Reference2ReferenceFunction<K, V> f) {
/* 292 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <K, V> Reference2ReferenceFunction<K, V> synchronize(Reference2ReferenceFunction<K, V> f, Object sync) {
/* 305 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K, V>
/*     */     extends AbstractReference2ReferenceFunction<K, V> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ReferenceFunction<? extends K, ? extends V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Reference2ReferenceFunction<? extends K, ? extends V> f) {
/* 314 */       if (f == null) throw new NullPointerException(); 
/* 315 */       this.function = f;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 320 */       return this.function.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public V defaultReturnValue() {
/* 325 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 330 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 335 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(K k, V v) {
/* 340 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(Object k) {
/* 345 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object k, V defaultValue) {
/* 351 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(Object k) {
/* 356 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 361 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 366 */       return this.function.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 371 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 376 */       return this.function.toString();
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
/*     */   public static <K, V> Reference2ReferenceFunction<K, V> unmodifiable(Reference2ReferenceFunction<? extends K, ? extends V> f) {
/* 388 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */