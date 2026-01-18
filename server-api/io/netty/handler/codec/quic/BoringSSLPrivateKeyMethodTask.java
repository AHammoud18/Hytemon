/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.util.function.BiConsumer;
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
/*    */ abstract class BoringSSLPrivateKeyMethodTask
/*    */   extends BoringSSLTask
/*    */ {
/*    */   private final BoringSSLPrivateKeyMethod method;
/*    */   private byte[] resultBytes;
/*    */   
/*    */   BoringSSLPrivateKeyMethodTask(long ssl, BoringSSLPrivateKeyMethod method) {
/* 28 */     super(ssl);
/* 29 */     this.method = method;
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void runTask(long ssl, BoringSSLTask.TaskCallback callback) {
/* 34 */     runMethod(ssl, this.method, (result, error) -> {
/*    */           if (result == null || error != null) {
/*    */             callback.onResult(ssl, -1);
/*    */           } else {
/*    */             this.resultBytes = result;
/*    */             callback.onResult(ssl, 1);
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   protected abstract void runMethod(long paramLong, BoringSSLPrivateKeyMethod paramBoringSSLPrivateKeyMethod, BiConsumer<byte[], Throwable> paramBiConsumer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLPrivateKeyMethodTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */