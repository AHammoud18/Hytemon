/*    */ package io.netty.util.concurrent;
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
/*    */ public final class SucceededFuture<V>
/*    */   extends CompleteFuture<V>
/*    */ {
/*    */   private final V result;
/*    */   
/*    */   public SucceededFuture(EventExecutor executor, V result) {
/* 32 */     super(executor);
/* 33 */     this.result = result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable cause() {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuccess() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public V getNow() {
/* 48 */     return this.result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\SucceededFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */