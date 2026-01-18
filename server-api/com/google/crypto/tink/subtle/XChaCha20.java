/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class XChaCha20
/*    */   implements IndCpaCipher
/*    */ {
/*    */   static final int NONCE_LENGTH_IN_BYTES = 24;
/*    */   private final InsecureNonceXChaCha20 cipher;
/*    */   
/*    */   XChaCha20(byte[] key, int initialCounter) throws InvalidKeyException {
/* 44 */     this.cipher = new InsecureNonceXChaCha20(key, initialCounter);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] encrypt(byte[] plaintext) throws GeneralSecurityException {
/* 49 */     ByteBuffer output = ByteBuffer.allocate(24 + plaintext.length);
/* 50 */     byte[] nonce = Random.randBytes(24);
/* 51 */     output.put(nonce);
/* 52 */     this.cipher.encrypt(output, nonce, plaintext);
/* 53 */     return output.array();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] decrypt(byte[] ciphertext) throws GeneralSecurityException {
/* 58 */     if (ciphertext.length < 24) {
/* 59 */       throw new GeneralSecurityException("ciphertext too short");
/*    */     }
/* 61 */     byte[] nonce = Arrays.copyOf(ciphertext, 24);
/*    */     
/* 63 */     ByteBuffer rawCiphertext = ByteBuffer.wrap(ciphertext, 24, ciphertext.length - 24);
/*    */     
/* 65 */     return this.cipher.decrypt(nonce, rawCiphertext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\XChaCha20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */