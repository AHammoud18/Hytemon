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
/*    */ 
/*    */ final class BoringSSLCertificateCallbackTask
/*    */   extends BoringSSLTask
/*    */ {
/*    */   private final byte[] keyTypeBytes;
/*    */   private final byte[][] asn1DerEncodedPrincipals;
/*    */   private final String[] authMethods;
/*    */   private final BoringSSLCertificateCallback callback;
/*    */   private long key;
/*    */   private long chain;
/*    */   
/*    */   BoringSSLCertificateCallbackTask(long ssl, byte[] keyTypeBytes, byte[][] asn1DerEncodedPrincipals, String[] authMethods, BoringSSLCertificateCallback callback) {
/* 34 */     super(ssl);
/*    */     
/* 36 */     this.keyTypeBytes = keyTypeBytes;
/* 37 */     this.asn1DerEncodedPrincipals = asn1DerEncodedPrincipals;
/* 38 */     this.authMethods = authMethods;
/* 39 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runTask(long ssl, BoringSSLTask.TaskCallback taskCallback) {
/*    */     try {
/* 46 */       long[] result = this.callback.handle(ssl, this.keyTypeBytes, this.asn1DerEncodedPrincipals, this.authMethods);
/* 47 */       if (result == null) {
/* 48 */         taskCallback.onResult(ssl, 0);
/*    */       } else {
/* 50 */         this.key = result[0];
/* 51 */         this.chain = result[1];
/* 52 */         taskCallback.onResult(ssl, 1);
/*    */       } 
/* 54 */     } catch (Exception e) {
/*    */ 
/*    */ 
/*    */       
/* 58 */       taskCallback.onResult(ssl, 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void destroy() {
/* 64 */     if (this.key != 0L) {
/* 65 */       BoringSSL.EVP_PKEY_free(this.key);
/* 66 */       this.key = 0L;
/*    */     } 
/* 68 */     if (this.chain != 0L) {
/* 69 */       BoringSSL.CRYPTO_BUFFER_stack_free(this.chain);
/* 70 */       this.chain = 0L;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLCertificateCallbackTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */