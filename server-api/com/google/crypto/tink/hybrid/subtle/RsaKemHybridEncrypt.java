/*    */ package com.google.crypto.tink.hybrid.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.Aead;
/*    */ import com.google.crypto.tink.HybridEncrypt;
/*    */ import com.google.crypto.tink.aead.subtle.AeadFactory;
/*    */ import com.google.crypto.tink.subtle.Hkdf;
/*    */ import java.math.BigInteger;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.interfaces.RSAPublicKey;
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
/*    */ public final class RsaKemHybridEncrypt
/*    */   implements HybridEncrypt
/*    */ {
/*    */   private final RSAPublicKey recipientPublicKey;
/*    */   private final String hkdfHmacAlgo;
/*    */   private final byte[] hkdfSalt;
/*    */   private final AeadFactory aeadFactory;
/*    */   
/*    */   public RsaKemHybridEncrypt(RSAPublicKey recipientPublicKey, String hkdfHmacAlgo, byte[] hkdfSalt, AeadFactory aeadFactory) throws GeneralSecurityException {
/* 46 */     RsaKem.validateRsaModulus(recipientPublicKey.getModulus());
/* 47 */     this.recipientPublicKey = recipientPublicKey;
/* 48 */     this.hkdfHmacAlgo = hkdfHmacAlgo;
/* 49 */     this.hkdfSalt = hkdfSalt;
/* 50 */     this.aeadFactory = aeadFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] encrypt(byte[] plaintext, byte[] contextInfo) throws GeneralSecurityException {
/* 57 */     BigInteger mod = this.recipientPublicKey.getModulus();
/* 58 */     byte[] sharedSecret = RsaKem.generateSecret(mod);
/*    */ 
/*    */     
/* 61 */     byte[] token = RsaKem.rsaEncrypt(this.recipientPublicKey, sharedSecret);
/*    */ 
/*    */ 
/*    */     
/* 65 */     byte[] demKey = Hkdf.computeHkdf(this.hkdfHmacAlgo, sharedSecret, this.hkdfSalt, contextInfo, this.aeadFactory
/* 66 */         .getKeySizeInBytes());
/*    */     
/* 68 */     Aead aead = this.aeadFactory.createAead(demKey);
/* 69 */     byte[] ciphertext = aead.encrypt(plaintext, RsaKem.EMPTY_AAD);
/* 70 */     return ByteBuffer.allocate(token.length + ciphertext.length).put(token).put(ciphertext).array();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\subtle\RsaKemHybridEncrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */