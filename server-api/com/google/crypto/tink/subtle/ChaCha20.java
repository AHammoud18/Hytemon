/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.aead.internal.InsecureNonceChaCha20;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidKeyException;
/*    */ import java.util.Arrays;
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
/*    */ class ChaCha20
/*    */   implements IndCpaCipher
/*    */ {
/*    */   static final int NONCE_LENGTH_IN_BYTES = 12;
/*    */   private final InsecureNonceChaCha20 cipher;
/*    */   
/*    */   ChaCha20(byte[] key, int initialCounter) throws InvalidKeyException {
/* 36 */     this.cipher = new InsecureNonceChaCha20(key, initialCounter);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] encrypt(byte[] plaintext) throws GeneralSecurityException {
/* 41 */     ByteBuffer output = ByteBuffer.allocate(12 + plaintext.length);
/* 42 */     byte[] nonce = Random.randBytes(12);
/* 43 */     output.put(nonce);
/* 44 */     this.cipher.encrypt(output, nonce, plaintext);
/* 45 */     return output.array();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] decrypt(byte[] ciphertext) throws GeneralSecurityException {
/* 50 */     if (ciphertext.length < 12) {
/* 51 */       throw new GeneralSecurityException("ciphertext too short");
/*    */     }
/* 53 */     byte[] nonce = Arrays.copyOf(ciphertext, 12);
/*    */     
/* 55 */     ByteBuffer rawCiphertext = ByteBuffer.wrap(ciphertext, 12, ciphertext.length - 12);
/*    */     
/* 57 */     return this.cipher.decrypt(nonce, rawCiphertext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\ChaCha20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */