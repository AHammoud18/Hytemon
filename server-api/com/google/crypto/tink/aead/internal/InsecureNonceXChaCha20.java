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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InsecureNonceXChaCha20
/*    */   extends InsecureNonceChaCha20Base
/*    */ {
/*    */   public static final int NONCE_SIZE_IN_BYTES = 24;
/*    */   
/*    */   public InsecureNonceXChaCha20(byte[] key, int initialCounter) throws InvalidKeyException {
/* 42 */     super(key, initialCounter);
/*    */   }
/*    */ 
/*    */   
/*    */   int[] createInitialState(int[] nonce, int counter) {
/* 47 */     if (nonce.length != nonceSizeInBytes() / 4) {
/* 48 */       throw new IllegalArgumentException(
/* 49 */           String.format("XChaCha20 uses 192-bit nonces, but got a %d-bit nonce", new Object[] {
/* 50 */               Integer.valueOf(nonce.length * 32)
/*    */             }));
/*    */     }
/*    */     
/* 54 */     int[] state = new int[16];
/* 55 */     ChaCha20Util.setSigmaAndKey(state, ChaCha20Util.hChaCha20(this.key, nonce));
/* 56 */     state[12] = counter;
/* 57 */     state[13] = 0;
/* 58 */     state[14] = nonce[4];
/* 59 */     state[15] = nonce[5];
/* 60 */     return state;
/*    */   }
/*    */ 
/*    */   
/*    */   int nonceSizeInBytes() {
/* 65 */     return 24;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceXChaCha20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */