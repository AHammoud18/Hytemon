/*    */ package io.sentry.exception;
/*    */ 
/*    */ import io.sentry.protocol.Mechanism;
/*    */ import io.sentry.util.Objects;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class ExceptionMechanismException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 142345454265713915L;
/*    */   @NotNull
/*    */   private final Mechanism exceptionMechanism;
/*    */   @NotNull
/*    */   private final Throwable throwable;
/*    */   @NotNull
/*    */   private final Thread thread;
/*    */   private final boolean snapshot;
/*    */   
/*    */   public ExceptionMechanismException(@NotNull Mechanism mechanism, @NotNull Throwable throwable, @NotNull Thread thread, boolean snapshot) {
/* 34 */     this.exceptionMechanism = (Mechanism)Objects.requireNonNull(mechanism, "Mechanism is required.");
/* 35 */     this.throwable = (Throwable)Objects.requireNonNull(throwable, "Throwable is required.");
/* 36 */     this.thread = (Thread)Objects.requireNonNull(thread, "Thread is required.");
/* 37 */     this.snapshot = snapshot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExceptionMechanismException(@NotNull Mechanism mechanism, @NotNull Throwable throwable, @NotNull Thread thread) {
/* 51 */     this(mechanism, throwable, thread, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Mechanism getExceptionMechanism() {
/* 60 */     return this.exceptionMechanism;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Throwable getThrowable() {
/* 69 */     return this.throwable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Thread getThread() {
/* 78 */     return this.thread;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSnapshot() {
/* 87 */     return this.snapshot;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\exception\ExceptionMechanismException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */