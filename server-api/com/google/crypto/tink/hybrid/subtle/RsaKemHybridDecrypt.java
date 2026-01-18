/*     */ package com.google.crypto.tink.hybrid.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.HybridDecrypt;
/*     */ import com.google.crypto.tink.aead.subtle.AeadFactory;
/*     */ import com.google.crypto.tink.subtle.Hkdf;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.interfaces.RSAKey;
/*     */ import java.security.interfaces.RSAPrivateKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RsaKemHybridDecrypt
/*     */   implements HybridDecrypt
/*     */ {
/*     */   private final PrivateKey recipientPrivateKey;
/*     */   private final String hkdfHmacAlgo;
/*     */   private final byte[] hkdfSalt;
/*     */   private final AeadFactory aeadFactory;
/*     */   private final int modSizeInBytes;
/*     */   
/*     */   private RsaKemHybridDecrypt(PrivateKey recipientPrivateKey, String hkdfHmacAlgo, byte[] hkdfSalt, AeadFactory aeadFactory) throws GeneralSecurityException {
/*  50 */     BigInteger mod = ((RSAKey)recipientPrivateKey).getModulus();
/*  51 */     RsaKem.validateRsaModulus(mod);
/*     */     
/*  53 */     this.recipientPrivateKey = recipientPrivateKey;
/*  54 */     this.hkdfSalt = hkdfSalt;
/*  55 */     this.hkdfHmacAlgo = hkdfHmacAlgo;
/*  56 */     this.aeadFactory = aeadFactory;
/*  57 */     this.modSizeInBytes = RsaKem.bigIntSizeInBytes(mod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RsaKemHybridDecrypt(RSAPrivateKey recipientPrivateKey, String hkdfHmacAlgo, byte[] hkdfSalt, AeadFactory aeadFactory) throws GeneralSecurityException {
/*  66 */     this(recipientPrivateKey, hkdfHmacAlgo, hkdfSalt, aeadFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaKemHybridDecrypt create(PrivateKey recipientPrivateKey, String hkdfHmacAlgo, byte[] hkdfSalt, AeadFactory aeadFactory) throws GeneralSecurityException {
/*  81 */     if (!(recipientPrivateKey instanceof RSAKey)) {
/*  82 */       throw new InvalidKeyException("Must be an RSA private key");
/*     */     }
/*  84 */     return new RsaKemHybridDecrypt(recipientPrivateKey, hkdfHmacAlgo, hkdfSalt, aeadFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] contextInfo) throws GeneralSecurityException {
/*  90 */     if (ciphertext.length < this.modSizeInBytes) {
/*  91 */       throw new GeneralSecurityException(
/*  92 */           String.format("Ciphertext must be of at least size %d bytes, but got %d", new Object[] {
/*     */               
/*  94 */               Integer.valueOf(this.modSizeInBytes), Integer.valueOf(ciphertext.length)
/*     */             }));
/*     */     }
/*     */     
/*  98 */     ByteBuffer cipherBuffer = ByteBuffer.wrap(ciphertext);
/*  99 */     byte[] token = new byte[this.modSizeInBytes];
/* 100 */     cipherBuffer.get(token);
/*     */ 
/*     */     
/* 103 */     byte[] sharedSecret = RsaKem.rsaDecrypt(this.recipientPrivateKey, token);
/*     */ 
/*     */ 
/*     */     
/* 107 */     byte[] demKey = Hkdf.computeHkdf(this.hkdfHmacAlgo, sharedSecret, this.hkdfSalt, contextInfo, this.aeadFactory
/* 108 */         .getKeySizeInBytes());
/*     */ 
/*     */     
/* 111 */     Aead aead = this.aeadFactory.createAead(demKey);
/* 112 */     byte[] demPayload = new byte[cipherBuffer.remaining()];
/* 113 */     cipherBuffer.get(demPayload);
/* 114 */     return aead.decrypt(demPayload, RsaKem.EMPTY_AAD);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\subtle\RsaKemHybridDecrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */