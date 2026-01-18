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
/*    */ final class BoringSSLPrivateKeyMethodSignTask
/*    */   extends BoringSSLPrivateKeyMethodTask
/*    */ {
/*    */   private final int signatureAlgorithm;
/*    */   private final byte[] digest;
/*    */   
/*    */   BoringSSLPrivateKeyMethodSignTask(long ssl, int signatureAlgorithm, byte[] digest, BoringSSLPrivateKeyMethod method) {
/* 26 */     super(ssl, method);
/* 27 */     this.signatureAlgorithm = signatureAlgorithm;
/*    */     
/* 29 */     this.digest = digest;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void runMethod(long ssl, BoringSSLPrivateKeyMethod method, BiConsumer<byte[], Throwable> callback) {
/* 34 */     method.sign(ssl, this.signatureAlgorithm, this.digest, callback);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLPrivateKeyMethodSignTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */