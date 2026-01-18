/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.aead.internal.InsecureNonceAesGcmJce;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidAlgorithmParameterException;
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
/*    */ final class AesGcmHpkeAead
/*    */   implements HpkeAead
/*    */ {
/*    */   private final int keyLength;
/*    */   
/*    */   AesGcmHpkeAead(int keyLength) throws InvalidAlgorithmParameterException {
/* 30 */     if (keyLength != 16 && keyLength != 32) {
/* 31 */       throw new InvalidAlgorithmParameterException("Unsupported key length: " + keyLength);
/*    */     }
/* 33 */     this.keyLength = keyLength;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] seal(byte[] key, byte[] nonce, byte[] plaintext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 40 */     if (key.length != this.keyLength) {
/* 41 */       throw new InvalidAlgorithmParameterException("Unexpected key length: " + key.length);
/*    */     }
/* 43 */     InsecureNonceAesGcmJce aead = new InsecureNonceAesGcmJce(key);
/* 44 */     return aead.encrypt(nonce, plaintext, ciphertextOffset, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] open(byte[] key, byte[] nonce, byte[] ciphertext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 51 */     if (key.length != this.keyLength) {
/* 52 */       throw new InvalidAlgorithmParameterException("Unexpected key length: " + key.length);
/*    */     }
/* 54 */     InsecureNonceAesGcmJce aead = new InsecureNonceAesGcmJce(key);
/* 55 */     return aead.decrypt(nonce, ciphertext, ciphertextOffset, associatedData);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getAeadId() throws GeneralSecurityException {
/* 60 */     switch (this.keyLength) {
/*    */       case 16:
/* 62 */         return HpkeUtil.AES_128_GCM_AEAD_ID;
/*    */       case 32:
/* 64 */         return HpkeUtil.AES_256_GCM_AEAD_ID;
/*    */     } 
/* 66 */     throw new GeneralSecurityException("Could not determine HPKE AEAD ID");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getKeyLength() {
/* 72 */     return this.keyLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNonceLength() {
/* 77 */     return 12;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\AesGcmHpkeAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */