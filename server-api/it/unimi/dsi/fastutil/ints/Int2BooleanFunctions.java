/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Int2BooleanFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractInt2BooleanFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean get(int k) {
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(int k, boolean defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(int k) {
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
/*  78 */       return Int2BooleanFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Int2BooleanFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractInt2BooleanFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final int key;
/*     */ 
/*     */     
/*     */     protected final boolean value;
/*     */ 
/*     */     
/*     */     protected Singleton(int key, boolean value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(int k) {
/* 128 */       return (this.key == k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean get(int k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(int k, boolean defaultValue) {
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
/*     */   public static Int2BooleanFunction singleton(int key, boolean value) {
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
/*     */   public static Int2BooleanFunction singleton(Integer key, Boolean value) {
/* 179 */     return new Singleton(key.intValue(), value.booleanValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction
/*     */     implements Int2BooleanFunction, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2BooleanFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Int2BooleanFunction f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Int2BooleanFunction f) {
/* 195 */       if (f == null) throw new NullPointerException(); 
/* 196 */       this.function = f;
/* 197 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(int operand) {
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
/*     */     public Boolean apply(Integer key) {
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
/*     */     public boolean containsKey(int k) {
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
/*     */     public boolean put(int k, boolean v) {
/* 258 */       synchronized (this.sync) {
/* 259 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean get(int k) {
/* 265 */       synchronized (this.sync) {
/* 266 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(int k, boolean defaultValue) {
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int k) {
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
/*     */     public Boolean put(Integer k, Boolean v) {
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
/*     */     public Boolean get(Object k) {
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
/*     */     public Boolean getOrDefault(Object k, Boolean defaultValue) {
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
/*     */     public Boolean remove(Object k) {
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
/*     */   public static Int2BooleanFunction synchronize(Int2BooleanFunction f) {
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
/*     */   public static Int2BooleanFunction synchronize(Int2BooleanFunction f, Object sync) {
/* 393 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction
/*     */     extends AbstractInt2BooleanFunction implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2BooleanFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Int2BooleanFunction f) {
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
/*     */     public boolean defaultReturnValue() {
/* 413 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
/* 418 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(int k) {
/* 423 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean put(int k, boolean v) {
/* 428 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean get(int k) {
/* 433 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(int k, boolean defaultValue) {
/* 438 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int k) {
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
/*     */     public Boolean put(Integer k, Boolean v) {
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
/*     */     public Boolean get(Object k) {
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
/*     */     public Boolean getOrDefault(Object k, Boolean defaultValue) {
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
/*     */     public Boolean remove(Object k) {
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
/*     */   public static Int2BooleanFunction unmodifiable(Int2BooleanFunction f) {
/* 519 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Int2BooleanFunction {
/*     */     protected final Function<? super Integer, ? extends Boolean> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Integer, ? extends Boolean> function) {
/* 527 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(int key) {
/* 532 */       return (this.function.apply(Integer.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 538 */       if (key == null) return false; 
/* 539 */       return (this.function.apply((Integer)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean get(int key) {
/* 544 */       Boolean v = this.function.apply(Integer.valueOf(key));
/* 545 */       if (v == null) return defaultReturnValue(); 
/* 546 */       return v.booleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(int key, boolean defaultValue) {
/* 551 */       Boolean v = this.function.apply(Integer.valueOf(key));
/* 552 */       if (v == null) return defaultValue; 
/* 553 */       return v.booleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean get(Object key) {
/* 559 */       if (key == null) return null; 
/* 560 */       return this.function.apply((Integer)key);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean getOrDefault(Object key, Boolean defaultValue) {
/* 566 */       if (key == null) return defaultValue; 
/*     */       Boolean v;
/* 568 */       return ((v = this.function.apply((Integer)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean put(Integer key, Boolean value) {
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
/*     */   public static Int2BooleanFunction primitive(Function<? super Integer, ? extends Boolean> f) {
/* 596 */     Objects.requireNonNull(f);
/* 597 */     if (f instanceof Int2BooleanFunction) return (Int2BooleanFunction)f; 
/* 598 */     if (f instanceof IntPredicate) { Objects.requireNonNull((IntPredicate)f); return (IntPredicate)f::test; }
/* 599 */      return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2BooleanFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */