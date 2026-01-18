/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.HybridDecrypt;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*     */ import com.google.crypto.tink.hybrid.HpkePrivateKey;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
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
/*     */ @Immutable
/*     */ public final class HpkeDecrypt
/*     */   implements HybridDecrypt
/*     */ {
/*  38 */   private static final byte[] EMPTY_ASSOCIATED_DATA = new byte[0];
/*     */ 
/*     */   
/*     */   private final HpkeKemPrivateKey recipientPrivateKey;
/*     */ 
/*     */   
/*     */   private final HpkeKem kem;
/*     */   
/*     */   private final HpkeKdf kdf;
/*     */   
/*     */   private final HpkeAead aead;
/*     */   
/*     */   private final int encapsulatedKeyLength;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private HpkeDecrypt(HpkeKemPrivateKey recipientPrivateKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, int encapsulatedKeyLength, Bytes outputPrefix) {
/*  56 */     this.recipientPrivateKey = recipientPrivateKey;
/*  57 */     this.kem = kem;
/*  58 */     this.kdf = kdf;
/*  59 */     this.aead = aead;
/*  60 */     this.encapsulatedKeyLength = encapsulatedKeyLength;
/*  61 */     this.outputPrefix = outputPrefix.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int encodingSizeInBytes(HpkeParameters.KemId kemId) throws GeneralSecurityException {
/*  66 */     if (kemId.equals(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)) {
/*  67 */       return 32;
/*     */     }
/*  69 */     if (kemId.equals(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)) {
/*  70 */       return 65;
/*     */     }
/*  72 */     if (kemId.equals(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)) {
/*  73 */       return 97;
/*     */     }
/*  75 */     if (kemId.equals(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)) {
/*  76 */       return 133;
/*     */     }
/*  78 */     throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static HpkeKemPrivateKey createHpkeKemPrivateKey(HpkePrivateKey privateKey) throws GeneralSecurityException {
/*  84 */     HpkeParameters.KemId kemId = privateKey.getParameters().getKemId();
/*  85 */     if (kemId.equals(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) || kemId
/*  86 */       .equals(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) || kemId
/*  87 */       .equals(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) || kemId
/*  88 */       .equals(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)) {
/*     */       
/*  90 */       Bytes convertedPrivateKeyBytes = Bytes.copyFrom(privateKey
/*  91 */           .getPrivateKeyBytes().toByteArray(InsecureSecretKeyAccess.get()));
/*  92 */       return new HpkeKemPrivateKey(convertedPrivateKeyBytes, privateKey
/*  93 */           .getPublicKey().getPublicKeyBytes());
/*     */     } 
/*  95 */     throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
/*     */   }
/*     */   
/*     */   public static HybridDecrypt create(HpkePrivateKey privateKey) throws GeneralSecurityException {
/*  99 */     HpkeParameters parameters = privateKey.getParameters();
/* 100 */     HpkeKem kem = HpkePrimitiveFactory.createKem(parameters.getKemId());
/* 101 */     HpkeKdf kdf = HpkePrimitiveFactory.createKdf(parameters.getKdfId());
/* 102 */     HpkeAead aead = HpkePrimitiveFactory.createAead(parameters.getAeadId());
/* 103 */     int encapsulatedKeyLength = encodingSizeInBytes(parameters.getKemId());
/* 104 */     HpkeKemPrivateKey recipientKemPrivateKey = createHpkeKemPrivateKey(privateKey);
/* 105 */     return new HpkeDecrypt(recipientKemPrivateKey, kem, kdf, aead, encapsulatedKeyLength, privateKey
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 111 */         .getOutputPrefix());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] contextInfo) throws GeneralSecurityException {
/* 117 */     int prefixAndHeaderLength = this.outputPrefix.length + this.encapsulatedKeyLength;
/* 118 */     if (ciphertext.length < prefixAndHeaderLength) {
/* 119 */       throw new GeneralSecurityException("Ciphertext is too short.");
/*     */     }
/* 121 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 122 */       throw new GeneralSecurityException("Invalid ciphertext (output prefix mismatch)");
/*     */     }
/* 124 */     byte[] info = contextInfo;
/* 125 */     if (info == null) {
/* 126 */       info = new byte[0];
/*     */     }
/*     */     
/* 129 */     byte[] encapsulatedKey = Arrays.copyOfRange(ciphertext, this.outputPrefix.length, prefixAndHeaderLength);
/*     */     
/* 131 */     HpkeContext context = HpkeContext.createRecipientContext(encapsulatedKey, this.recipientPrivateKey, this.kem, this.kdf, this.aead, info);
/*     */     
/* 133 */     return context.open(ciphertext, prefixAndHeaderLength, EMPTY_ASSOCIATED_DATA);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeDecrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */