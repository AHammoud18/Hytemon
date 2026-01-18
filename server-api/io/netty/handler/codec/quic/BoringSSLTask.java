/*    */ package io.netty.handler.codec.quic;
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
/*    */ abstract class BoringSSLTask
/*    */   implements Runnable
/*    */ {
/*    */   private final long ssl;
/*    */   protected boolean didRun;
/*    */   private int returnValue;
/*    */   private volatile boolean complete;
/*    */   
/*    */   protected BoringSSLTask(long ssl) {
/* 31 */     this.ssl = ssl;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void run() {
/* 36 */     if (!this.didRun) {
/* 37 */       this.didRun = true;
/* 38 */       runTask(this.ssl, (ssl, result) -> {
/*    */             this.returnValue = result;
/*    */             this.complete = true;
/*    */           });
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void destroy() {}
/*    */   
/*    */   protected abstract void runTask(long paramLong, TaskCallback paramTaskCallback);
/*    */   
/*    */   static interface TaskCallback {
/*    */     void onResult(long param1Long, int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */