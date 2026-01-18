/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*     */ import com.google.crypto.tink.hybrid.HpkePublicKey;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public final class HpkeHelperForAndroidKeystore
/*     */ {
/*  36 */   private static final byte[] EMPTY_ASSOCIATED_DATA = new byte[0];
/*     */   
/*     */   private final HpkeKem kem;
/*     */   
/*     */   private final HpkeKdf kdf;
/*     */   private final HpkeAead aead;
/*     */   private final byte[] publicKeyByteArray;
/*     */   
/*     */   private HpkeHelperForAndroidKeystore(HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] publicKeyByteArray) {
/*  45 */     this.kem = kem;
/*  46 */     this.kdf = kdf;
/*  47 */     this.aead = aead;
/*  48 */     this.publicKeyByteArray = publicKeyByteArray;
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
/*     */   @AccessesPartialKey
/*     */   public static HpkeHelperForAndroidKeystore create(HpkePublicKey receiverPublicKey) throws GeneralSecurityException {
/*  62 */     HpkeParameters parameters = receiverPublicKey.getParameters();
/*  63 */     validateParameters(parameters);
/*  64 */     HpkeKem kem = HpkePrimitiveFactory.createKem(parameters.getKemId());
/*  65 */     HpkeKdf kdf = HpkePrimitiveFactory.createKdf(parameters.getKdfId());
/*  66 */     HpkeAead aead = HpkePrimitiveFactory.createAead(parameters.getAeadId());
/*  67 */     return new HpkeHelperForAndroidKeystore(kem, kdf, aead, receiverPublicKey
/*  68 */         .getPublicKeyBytes().toByteArray());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateParameters(HpkeParameters parameters) throws GeneralSecurityException {
/*  73 */     if (!parameters.getKemId().equals(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)) {
/*  74 */       throw new GeneralSecurityException("HpkeHelperForAndroidKeystore currently only supports DHKEM_P256_HKDF_SHA256.");
/*     */     }
/*     */     
/*  77 */     if (!parameters.getKdfId().equals(HpkeParameters.KdfId.HKDF_SHA256)) {
/*  78 */       throw new GeneralSecurityException("HpkeHelperForAndroidKeystore currently only supports HKDF_SHA256.");
/*     */     }
/*     */     
/*  81 */     if (!parameters.getAeadId().equals(HpkeParameters.AeadId.AES_128_GCM) && 
/*  82 */       !parameters.getAeadId().equals(HpkeParameters.AeadId.AES_256_GCM)) {
/*  83 */       throw new GeneralSecurityException("HpkeHelperForAndroidKeystore currently only supports AES_128_GCM and AES_256_GCM.");
/*     */     }
/*     */     
/*  86 */     if (!parameters.getVariant().equals(HpkeParameters.Variant.NO_PREFIX)) {
/*  87 */       throw new GeneralSecurityException("HpkeHelperForAndroidKeystore currently only supports Variant.NO_PREFIX");
/*     */     }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptUnauthenticatedWithEncapsulatedKeyAndP256SharedSecret(byte[] encapsulatedKey, byte[] dhSharedSecret, byte[] ciphertext, int ciphertextOffset, byte[] contextInfo) throws GeneralSecurityException {
/* 106 */     byte[] info = contextInfo;
/* 107 */     if (info == null) {
/* 108 */       info = new byte[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 113 */     byte[] sharedSecret = NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P256).deriveKemSharedSecret(dhSharedSecret, encapsulatedKey, this.publicKeyByteArray);
/*     */     
/* 115 */     HpkeContext context = HpkeContext.createContext(HpkeUtil.BASE_MODE, encapsulatedKey, sharedSecret, this.kem, this.kdf, this.aead, info);
/*     */     
/* 117 */     return context.open(ciphertext, ciphertextOffset, EMPTY_ASSOCIATED_DATA);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeHelperForAndroidKeystore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */