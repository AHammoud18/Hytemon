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
/*    */ public class InsecureNonceChaCha20
/*    */   extends InsecureNonceChaCha20Base
/*    */ {
/*    */   public InsecureNonceChaCha20(byte[] key, int initialCounter) throws InvalidKeyException {
/* 28 */     super(key, initialCounter);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] createInitialState(int[] nonce, int counter) {
/* 33 */     if (nonce.length != nonceSizeInBytes() / 4) {
/* 34 */       throw new IllegalArgumentException(
/* 35 */           String.format("ChaCha20 uses 96-bit nonces, but got a %d-bit nonce", new Object[] { Integer.valueOf(nonce.length * 32) }));
/*    */     }
/*    */     
/* 38 */     int[] state = new int[16];
/*    */ 
/*    */ 
/*    */     
/* 42 */     ChaCha20Util.setSigmaAndKey(state, this.key);
/*    */ 
/*    */     
/* 45 */     state[12] = counter;
/*    */ 
/*    */ 
/*    */     
/* 49 */     System.arraycopy(nonce, 0, state, 13, nonce.length);
/* 50 */     return state;
/*    */   }
/*    */ 
/*    */   
/*    */   public int nonceSizeInBytes() {
/* 55 */     return 12;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceChaCha20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */