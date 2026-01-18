/*    */ package com.google.crypto.tink.aead.internal;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidKeyException;
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
/*    */ public final class InsecureNonceChaCha20Poly1305
/*    */   extends InsecureNonceChaCha20Poly1305Base
/*    */ {
/*    */   public InsecureNonceChaCha20Poly1305(byte[] key) throws GeneralSecurityException {
/* 31 */     super(key);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   InsecureNonceChaCha20Base newChaCha20Instance(byte[] key, int initialCounter) throws InvalidKeyException {
/* 37 */     return new InsecureNonceChaCha20(key, initialCounter);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceChaCha20Poly1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */