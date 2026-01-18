/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class Char2ByteFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractChar2ByteFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public byte get(char k) {
/*  44 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(char k, byte defaultValue) {
/*  49 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char k) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte defaultReturnValue() {
/*  59 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
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
/*  78 */       return Char2ByteFunctions.EMPTY_FUNCTION;
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
/*  98 */       return Char2ByteFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractChar2ByteFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final char key;
/*     */ 
/*     */     
/*     */     protected final byte value;
/*     */ 
/*     */     
/*     */     protected Singleton(char key, byte value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char k) {
/* 128 */       return (this.key == k);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte get(char k) {
/* 133 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(char k, byte defaultValue) {
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
/*     */   public static Char2ByteFunction singleton(char key, byte value) {
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
/*     */   public static Char2ByteFunction singleton(Character key, Byte value) {
/* 179 */     return new Singleton(key.charValue(), value.byteValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction
/*     */     implements Char2ByteFunction, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ByteFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Char2ByteFunction f, Object sync) {
/* 189 */       if (f == null) throw new NullPointerException(); 
/* 190 */       this.function = f;
/* 191 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedFunction(Char2ByteFunction f) {
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
/*     */     public Byte apply(Character key) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return (Byte)this.function.apply(key);
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
/*     */     public byte defaultReturnValue() {
/* 235 */       synchronized (this.sync) {
/* 236 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
/* 242 */       synchronized (this.sync) {
/* 243 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char k) {
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
/*     */     public byte put(char k, byte v) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte get(char k) {
/* 271 */       synchronized (this.sync) {
/* 272 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(char k, byte defaultValue) {
/* 278 */       synchronized (this.sync) {
/* 279 */         return this.function.getOrDefault(k, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte remove(char k) {
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
/*     */     public Byte put(Character k, Byte v) {
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
/*     */     public Byte get(Object k) {
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
/*     */     public Byte getOrDefault(Object k, Byte defaultValue) {
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
/*     */     public Byte remove(Object k) {
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
/*     */   public static Char2ByteFunction synchronize(Char2ByteFunction f) {
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
/*     */   public static Char2ByteFunction synchronize(Char2ByteFunction f, Object sync) {
/* 399 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction
/*     */     extends AbstractChar2ByteFunction implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ByteFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Char2ByteFunction f) {
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
/*     */     public byte defaultReturnValue() {
/* 419 */       return this.function.defaultReturnValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
/* 424 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char k) {
/* 429 */       return this.function.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte put(char k, byte v) {
/* 434 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte get(char k) {
/* 439 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(char k, byte defaultValue) {
/* 444 */       return this.function.getOrDefault(k, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte remove(char k) {
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
/*     */     public Byte put(Character k, Byte v) {
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
/*     */     public Byte get(Object k) {
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
/*     */     public Byte getOrDefault(Object k, Byte defaultValue) {
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
/*     */     public Byte remove(Object k) {
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
/*     */   public static Char2ByteFunction unmodifiable(Char2ByteFunction f) {
/* 525 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Char2ByteFunction {
/*     */     protected final Function<? super Character, ? extends Byte> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Character, ? extends Byte> function) {
/* 533 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char key) {
/* 538 */       return (this.function.apply(Character.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 544 */       if (key == null) return false; 
/* 545 */       return (this.function.apply((Character)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte get(char key) {
/* 550 */       Byte v = this.function.apply(Character.valueOf(key));
/* 551 */       if (v == null) return defaultReturnValue(); 
/* 552 */       return v.byteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(char key, byte defaultValue) {
/* 557 */       Byte v = this.function.apply(Character.valueOf(key));
/* 558 */       if (v == null) return defaultValue; 
/* 559 */       return v.byteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte get(Object key) {
/* 565 */       if (key == null) return null; 
/* 566 */       return this.function.apply((Character)key);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte getOrDefault(Object key, Byte defaultValue) {
/* 572 */       if (key == null) return defaultValue; 
/*     */       Byte v;
/* 574 */       return ((v = this.function.apply((Character)key)) == null) ? defaultValue : v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte put(Character key, Byte value) {
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
/*     */   public static Char2ByteFunction primitive(Function<? super Character, ? extends Byte> f) {
/* 602 */     Objects.requireNonNull(f);
/* 603 */     if (f instanceof Char2ByteFunction) return (Char2ByteFunction)f; 
/* 604 */     if (f instanceof IntUnaryOperator) return key -> SafeMath.safeIntToByte(((IntUnaryOperator)f).applyAsInt(key)); 
/* 605 */     return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ByteFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */