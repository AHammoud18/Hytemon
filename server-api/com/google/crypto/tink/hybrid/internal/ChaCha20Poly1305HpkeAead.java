/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.aead.internal.InsecureNonceChaCha20Poly1305;
/*    */ import com.google.crypto.tink.aead.internal.InsecureNonceChaCha20Poly1305Jce;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidAlgorithmParameterException;
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
/*    */ @Immutable
/*    */ final class ChaCha20Poly1305HpkeAead
/*    */   implements HpkeAead
/*    */ {
/*    */   public byte[] seal(byte[] key, byte[] nonce, byte[] plaintext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 33 */     if (key.length != getKeyLength()) {
/* 34 */       throw new InvalidAlgorithmParameterException("Unexpected key length: " + getKeyLength());
/*    */     }
/* 36 */     if (InsecureNonceChaCha20Poly1305Jce.isSupported()) {
/* 37 */       InsecureNonceChaCha20Poly1305Jce insecureNonceChaCha20Poly1305Jce = InsecureNonceChaCha20Poly1305Jce.create(key);
/* 38 */       return insecureNonceChaCha20Poly1305Jce.encrypt(nonce, plaintext, ciphertextOffset, associatedData);
/*    */     } 
/* 40 */     InsecureNonceChaCha20Poly1305 aead = new InsecureNonceChaCha20Poly1305(key);
/* 41 */     byte[] aeadCiphertext = aead.encrypt(nonce, plaintext, associatedData);
/* 42 */     if (aeadCiphertext.length > Integer.MAX_VALUE - ciphertextOffset) {
/* 43 */       throw new InvalidAlgorithmParameterException("Plaintext too long");
/*    */     }
/* 45 */     byte[] ciphertext = new byte[ciphertextOffset + aeadCiphertext.length];
/* 46 */     System.arraycopy(aeadCiphertext, 0, ciphertext, ciphertextOffset, aeadCiphertext.length);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     return ciphertext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] open(byte[] key, byte[] nonce, byte[] ciphertext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 59 */     if (key.length != getKeyLength()) {
/* 60 */       throw new InvalidAlgorithmParameterException("Unexpected key length: " + getKeyLength());
/*    */     }
/* 62 */     if (InsecureNonceChaCha20Poly1305Jce.isSupported()) {
/* 63 */       InsecureNonceChaCha20Poly1305Jce insecureNonceChaCha20Poly1305Jce = InsecureNonceChaCha20Poly1305Jce.create(key);
/* 64 */       return insecureNonceChaCha20Poly1305Jce.decrypt(nonce, ciphertext, ciphertextOffset, associatedData);
/*    */     } 
/* 66 */     byte[] aeadCiphertext = Arrays.copyOfRange(ciphertext, ciphertextOffset, ciphertext.length);
/* 67 */     InsecureNonceChaCha20Poly1305 aead = new InsecureNonceChaCha20Poly1305(key);
/* 68 */     return aead.decrypt(nonce, aeadCiphertext, associatedData);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getAeadId() {
/* 73 */     return HpkeUtil.CHACHA20_POLY1305_AEAD_ID;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getKeyLength() {
/* 79 */     return 32;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNonceLength() {
/* 85 */     return 12;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\ChaCha20Poly1305HpkeAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */