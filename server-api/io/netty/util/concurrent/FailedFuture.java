/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ public final class FailedFuture<V>
/*    */   extends CompleteFuture<V>
/*    */ {
/*    */   private final Throwable cause;
/*    */   
/*    */   public FailedFuture(EventExecutor executor, Throwable cause) {
/* 37 */     super(executor);
/* 38 */     this.cause = (Throwable)ObjectUtil.checkNotNull(cause, "cause");
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable cause() {
/* 43 */     return this.cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuccess() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<V> sync() {
/* 53 */     PlatformDependent.throwException(this.cause);
/* 54 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<V> syncUninterruptibly() {
/* 59 */     PlatformDependent.throwException(this.cause);
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public V getNow() {
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\FailedFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */