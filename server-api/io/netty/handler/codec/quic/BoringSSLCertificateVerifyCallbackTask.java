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
/*    */ final class BoringSSLCertificateVerifyCallbackTask
/*    */   extends BoringSSLTask
/*    */ {
/*    */   private final byte[][] x509;
/*    */   private final String authAlgorithm;
/*    */   private final BoringSSLCertificateVerifyCallback verifier;
/*    */   
/*    */   BoringSSLCertificateVerifyCallbackTask(long ssl, byte[][] x509, String authAlgorithm, BoringSSLCertificateVerifyCallback verifier) {
/* 29 */     super(ssl);
/* 30 */     this.x509 = x509;
/* 31 */     this.authAlgorithm = authAlgorithm;
/* 32 */     this.verifier = verifier;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void runTask(long ssl, BoringSSLTask.TaskCallback callback) {
/* 37 */     int result = this.verifier.verify(ssl, this.x509, this.authAlgorithm);
/* 38 */     callback.onResult(ssl, result);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLCertificateVerifyCallbackTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */