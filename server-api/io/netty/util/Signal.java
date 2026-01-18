/*     */ package io.netty.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Signal
/*     */   extends Error
/*     */   implements Constant<Signal>
/*     */ {
/*     */   private static final long serialVersionUID = -221145131122459977L;
/*     */   
/*  27 */   private static final ConstantPool<Signal> pool = new ConstantPool<Signal>()
/*     */     {
/*     */       protected Signal newConstant(int id, String name) {
/*  30 */         return new Signal(id, name);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final SignalConstant constant;
/*     */   
/*     */   public static Signal valueOf(String name) {
/*  38 */     return pool.valueOf(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Signal valueOf(Class<?> firstNameComponent, String secondNameComponent) {
/*  45 */     return pool.valueOf(firstNameComponent, secondNameComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Signal(int id, String name) {
/*  54 */     this.constant = new SignalConstant(id, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expect(Signal signal) {
/*  62 */     if (this != signal) {
/*  63 */       throw new IllegalStateException("unexpected signal: " + signal);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable initCause(Throwable cause) {
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable fillInStackTrace() {
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int id() {
/*  81 */     return this.constant.id();
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  86 */     return this.constant.name();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  91 */     return (this == obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     return System.identityHashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Signal other) {
/* 101 */     if (this == other) {
/* 102 */       return 0;
/*     */     }
/*     */     
/* 105 */     return this.constant.compareTo(other.constant);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return name();
/*     */   }
/*     */   
/*     */   private static final class SignalConstant extends AbstractConstant<SignalConstant> {
/*     */     SignalConstant(int id, String name) {
/* 115 */       super(id, name);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\Signal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */