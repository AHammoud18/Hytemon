/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Byte2CharFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractByte2CharFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public char get(byte k) {
/*  44 */       return Character.MIN_VALUE;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getOrDefault(byte k, char defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(byte k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public char defaultReturnValue() {
/*  59 */       return Character.MIN_VALUE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(char defRetValue) {
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
/*  78 */       return Byte2CharFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Byte2CharFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractByte2CharFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final byte key;
/*     */ 
/*     */     
/*     */     protected final char value;
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, char value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(byte k) {
/* 128 */       return (this.key == k);
/*     */     }
/*     */ 
/*     */     
/*     */     public char get(byte k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getOrDefault(byte k, char defaultValue) {
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
/*     */   public static Byte2CharFunction singleton(byte key, char value) {
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
/*     */   public static Byte2CharFunction singleton(Byte key, Character value) {
/* 179 */     return new Singleton(key.byteValue(), value.charValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction
/*     */     implements Byte2CharFunction, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2CharFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Byte2CharFunction f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Byte2CharFunction f) {
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
/*     */     public int applyAsInt(int operand) {
/* 208 */       synchronized (this.sync) {
/* 209 */         return this.function.applyAsInt(operand);
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
/*     */     public Character apply(Byte key) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return (Character)this.function.apply(key);
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
/*     */     public char defaultReturnValue() {
/* 235 */       synchronized (this.sync) {
/* 236 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(char defRetValue) {
/* 242 */       synchronized (this.sync) {
/* 243 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(byte k) {
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
/*     */     public char put(byte k, char v) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char get(byte k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char getOrDefault(byte k, char defaultValue) {
/* 278 */       synchronized (this.sync) {
/* 279 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char remove(byte k) {
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
/*     */     public Character put(Byte k, Character v) {
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
/*     */     public Character get(Object k) {
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
/*     */     public Character getOrDefault(Object k, Character defaultValue) {
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
/*     */     public Character remove(Object k) {
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
/*     */   public static Byte2CharFunction synchronize(Byte2CharFunction f) {
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
/*     */   public static Byte2CharFunction synchronize(Byte2CharFunction f, Object sync) {
/* 399 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction
/*     */     extends AbstractByte2CharFunction implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2CharFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Byte2CharFunction f) {
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
/*     */     public char defaultReturnValue() {
/* 419 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(char defRetValue) {
/* 424 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(byte k) {
/* 429 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public char put(byte k, char v) {
/* 434 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char get(byte k) {
/* 439 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public char getOrDefault(byte k, char defaultValue) {
/* 444 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public char remove(byte k) {
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
/*     */     public Character put(Byte k, Character v) {
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
/*     */     public Character get(Object k) {
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
/*     */     public Character getOrDefault(Object k, Character defaultValue) {
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
/*     */     public Character remove(Object k) {
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
/*     */   public static Byte2CharFunction unmodifiable(Byte2CharFunction f) {
/* 525 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Byte2CharFunction {
/*     */     protected final Function<? super Byte, ? extends Character> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Byte, ? extends Character> function) {
/* 533 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(byte key) {
/* 538 */       return (this.function.apply(Byte.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 544 */       if (key == null) return false; 
/* 545 */       return (this.function.apply((Byte)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public char get(byte key) {
/* 550 */       Character v = this.function.apply(Byte.valueOf(key));
/* 551 */       if (v == null) return defaultReturnValue(); 
/* 552 */       return v.charValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public char getOrDefault(byte key, char defaultValue) {
/* 557 */       Character v = this.function.apply(Byte.valueOf(key));
/* 558 */       if (v == null) return defaultValue; 
/* 559 */       return v.charValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character get(Object key) {
/* 565 */       if (key == null) return null; 
/* 566 */       return this.function.apply((Byte)key);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character getOrDefault(Object key, Character defaultValue) {
/* 572 */       if (key == null) return defaultValue; 
/*     */       Character v;
/* 574 */       return ((v = this.function.apply((Byte)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character put(Byte key, Character value) {
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
/*     */   public static Byte2CharFunction primitive(Function<? super Byte, ? extends Character> f) {
/* 602 */     Objects.requireNonNull(f);
/* 603 */     if (f instanceof Byte2CharFunction) return (Byte2CharFunction)f; 
/* 604 */     if (f instanceof IntUnaryOperator) return key -> SafeMath.safeIntToChar(((IntUnaryOperator)f).applyAsInt(key)); 
/* 605 */     return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2CharFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */