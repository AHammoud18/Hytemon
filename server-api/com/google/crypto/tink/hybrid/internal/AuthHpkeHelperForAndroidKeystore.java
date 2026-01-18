/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*     */ import com.google.crypto.tink.hybrid.HpkePublicKey;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
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
/*     */ 
/*     */ @Immutable
/*     */ public final class AuthHpkeHelperForAndroidKeystore
/*     */ {
/*  41 */   private static final byte[] EMPTY_ASSOCIATED_DATA = new byte[0];
/*     */ 
/*     */   
/*     */   private final HpkeKem kem;
/*     */ 
/*     */   
/*     */   private final HpkeKdf kdf;
/*     */ 
/*     */   
/*     */   private final HpkeAead aead;
/*     */ 
/*     */   
/*     */   private final byte[] ourPublicKeyByteArray;
/*     */   
/*     */   private final byte[] theirPublicKeyByteArray;
/*     */ 
/*     */   
/*     */   private AuthHpkeHelperForAndroidKeystore(HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] ourPublicKeyByteArray, byte[] theirPublicKeyByteArray) {
/*  59 */     this.kem = kem;
/*  60 */     this.kdf = kdf;
/*  61 */     this.aead = aead;
/*  62 */     this.ourPublicKeyByteArray = ourPublicKeyByteArray;
/*  63 */     this.theirPublicKeyByteArray = theirPublicKeyByteArray;
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
/*     */   public static AuthHpkeHelperForAndroidKeystore create(HpkePublicKey ourPublicKey, HpkePublicKey theirPublicKey) throws GeneralSecurityException {
/*  77 */     if (!ourPublicKey.getParameters().equals(theirPublicKey.getParameters())) {
/*  78 */       throw new GeneralSecurityException("ourPublicKey.getParameters() must be equal to theirPublicKey.getParameters()");
/*     */     }
/*     */     
/*  81 */     HpkeParameters parameters = ourPublicKey.getParameters();
/*  82 */     validateParameters(parameters);
/*  83 */     HpkeKem kem = HpkePrimitiveFactory.createKem(parameters.getKemId());
/*  84 */     HpkeKdf kdf = HpkePrimitiveFactory.createKdf(parameters.getKdfId());
/*  85 */     HpkeAead aead = HpkePrimitiveFactory.createAead(parameters.getAeadId());
/*  86 */     return new AuthHpkeHelperForAndroidKeystore(kem, kdf, aead, ourPublicKey
/*     */ 
/*     */ 
/*     */         
/*  90 */         .getPublicKeyBytes().toByteArray(), theirPublicKey
/*  91 */         .getPublicKeyBytes().toByteArray());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateParameters(HpkeParameters parameters) throws GeneralSecurityException {
/*  96 */     if (!parameters.getKemId().equals(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)) {
/*  97 */       throw new GeneralSecurityException("AuthHpkeHelperForAndroidKeystore currently only supports KemId.DHKEM_P256_HKDF_SHA256.");
/*     */     }
/*     */     
/* 100 */     if (!parameters.getKdfId().equals(HpkeParameters.KdfId.HKDF_SHA256)) {
/* 101 */       throw new GeneralSecurityException("AuthHpkeHelperForAndroidKeystore currently only supports KdfId.HKDF_SHA256.");
/*     */     }
/*     */     
/* 104 */     if (!parameters.getAeadId().equals(HpkeParameters.AeadId.AES_128_GCM) && 
/* 105 */       !parameters.getAeadId().equals(HpkeParameters.AeadId.AES_256_GCM)) {
/* 106 */       throw new GeneralSecurityException("AuthHpkeHelperForAndroidKeystore currently only supports AeadId.AES_128_GCM and AeadId.AES_256_GCM.");
/*     */     }
/*     */ 
/*     */     
/* 110 */     if (!parameters.getVariant().equals(HpkeParameters.Variant.NO_PREFIX)) {
/* 111 */       throw new GeneralSecurityException("AuthHpkeHelperForAndroidKeystore currently only supports Variant.NO_PREFIX");
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
/*     */ 
/*     */   
/*     */   public byte[] decryptAuthenticatedWithEncapsulatedKeyAndP256SharedSecret(byte[] encapsulatedKey, byte[] dhSharedSecret1, byte[] dhSharedSecret2, byte[] ciphertext, int ciphertextOffset, byte[] info) throws GeneralSecurityException {
/* 132 */     byte[] dhSharedSecret = Bytes.concat(new byte[][] { dhSharedSecret1, dhSharedSecret2 });
/*     */ 
/*     */     
/* 135 */     byte[] derivedSharedSecret = NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P256).deriveKemSharedSecret(dhSharedSecret, encapsulatedKey, this.ourPublicKeyByteArray, this.theirPublicKeyByteArray);
/*     */ 
/*     */     
/* 138 */     HpkeContext context = HpkeContext.createContext(HpkeUtil.AUTH_MODE, encapsulatedKey, derivedSharedSecret, this.kem, this.kdf, this.aead, info);
/*     */     
/* 140 */     return context.open(ciphertext, ciphertextOffset, EMPTY_ASSOCIATED_DATA);
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
/*     */ 
/*     */   
/*     */   public byte[] encryptAuthenticatedWithEncapsulatedKeyAndP256SharedSecret(ECPoint emphemeralPublicKey, byte[] dhSharedSecret1, byte[] dhSharedSecret2, byte[] plaintext, byte[] contextInfo) throws GeneralSecurityException {
/* 160 */     byte[] emphemeralPublicKeyByteArray = EllipticCurves.pointEncode(EllipticCurves.CurveType.NIST_P256, EllipticCurves.PointFormatType.UNCOMPRESSED, emphemeralPublicKey);
/*     */     
/* 162 */     byte[] dhSharedSecret = Bytes.concat(new byte[][] { dhSharedSecret1, dhSharedSecret2 });
/*     */ 
/*     */     
/* 165 */     byte[] derivedSharedSecret = NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P256).deriveKemSharedSecret(dhSharedSecret, emphemeralPublicKeyByteArray, this.theirPublicKeyByteArray, this.ourPublicKeyByteArray);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     HpkeContext context = HpkeContext.createContext(HpkeUtil.AUTH_MODE, emphemeralPublicKeyByteArray, derivedSharedSecret, this.kem, this.kdf, this.aead, contextInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     return Bytes.concat(new byte[][] { emphemeralPublicKeyByteArray, context
/* 180 */           .seal(plaintext, EMPTY_ASSOCIATED_DATA) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\AuthHpkeHelperForAndroidKeystore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */