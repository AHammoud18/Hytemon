/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
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
/*     */ final class NistCurvesHpkeKem
/*     */   implements HpkeKem
/*     */ {
/*     */   private final EllipticCurves.CurveType curve;
/*     */   private final HkdfHpkeKdf hkdf;
/*     */   
/*     */   static NistCurvesHpkeKem fromCurve(EllipticCurves.CurveType curve) throws GeneralSecurityException {
/*  37 */     switch (curve) {
/*     */       case NIST_P256:
/*  39 */         return new NistCurvesHpkeKem(new HkdfHpkeKdf("HmacSha256"), EllipticCurves.CurveType.NIST_P256);
/*     */       case NIST_P384:
/*  41 */         return new NistCurvesHpkeKem(new HkdfHpkeKdf("HmacSha384"), EllipticCurves.CurveType.NIST_P384);
/*     */       case NIST_P521:
/*  43 */         return new NistCurvesHpkeKem(new HkdfHpkeKdf("HmacSha512"), EllipticCurves.CurveType.NIST_P521);
/*     */     } 
/*  45 */     throw new GeneralSecurityException("invalid curve type: " + curve);
/*     */   }
/*     */   
/*     */   private NistCurvesHpkeKem(HkdfHpkeKdf hkdf, EllipticCurves.CurveType curve) {
/*  49 */     this.hkdf = hkdf;
/*  50 */     this.curve = curve;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] deriveKemSharedSecret(byte[] dhSharedSecret, byte[] senderEphemeralPublicKey, byte[] recipientPublicKey) throws GeneralSecurityException {
/*  56 */     byte[] kemContext = Bytes.concat(new byte[][] { senderEphemeralPublicKey, recipientPublicKey });
/*  57 */     return extractAndExpand(dhSharedSecret, kemContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] deriveKemSharedSecret(byte[] dhSharedSecret, byte[] senderEphemeralPublicKey, byte[] recipientPublicKey, byte[] senderPublicKey) throws GeneralSecurityException {
/*  66 */     byte[] kemContext = Bytes.concat(new byte[][] { senderEphemeralPublicKey, recipientPublicKey, senderPublicKey });
/*  67 */     return extractAndExpand(dhSharedSecret, kemContext);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] extractAndExpand(byte[] dhSharedSecret, byte[] kemContext) throws GeneralSecurityException {
/*  72 */     byte[] kemSuiteID = HpkeUtil.kemSuiteId(getKemId());
/*  73 */     return this.hkdf.extractAndExpand(null, dhSharedSecret, "eae_prk", kemContext, "shared_secret", kemSuiteID, this.hkdf
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         .getMacLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HpkeKemEncapOutput encapsulate(byte[] recipientPublicKey, KeyPair senderEphemeralKeyPair) throws GeneralSecurityException {
/*  87 */     ECPublicKey recipientECPublicKey = EllipticCurves.getEcPublicKey(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, recipientPublicKey);
/*     */     
/*  89 */     byte[] dhSharedSecret = EllipticCurves.computeSharedSecret((ECPrivateKey)senderEphemeralKeyPair
/*  90 */         .getPrivate(), recipientECPublicKey);
/*     */     
/*  92 */     byte[] senderPublicKey = EllipticCurves.pointEncode(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, ((ECPublicKey)senderEphemeralKeyPair
/*     */ 
/*     */         
/*  95 */         .getPublic()).getW());
/*     */     
/*  97 */     byte[] kemSharedSecret = deriveKemSharedSecret(dhSharedSecret, senderPublicKey, recipientPublicKey);
/*  98 */     return new HpkeKemEncapOutput(kemSharedSecret, senderPublicKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkeKemEncapOutput encapsulate(byte[] recipientPublicKey) throws GeneralSecurityException {
/* 103 */     KeyPair keyPair = EllipticCurves.generateKeyPair(this.curve);
/* 104 */     return encapsulate(recipientPublicKey, keyPair);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HpkeKemEncapOutput authEncapsulate(byte[] recipientPublicKey, KeyPair senderEphemeralKeyPair, HpkeKemPrivateKey senderPrivateKey) throws GeneralSecurityException {
/* 112 */     ECPublicKey recipientECPublicKey = EllipticCurves.getEcPublicKey(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, recipientPublicKey);
/*     */     
/* 114 */     ECPrivateKey privateKey = EllipticCurves.getEcPrivateKey(this.curve, senderPrivateKey
/* 115 */         .getSerializedPrivate().toByteArray());
/*     */     
/* 117 */     byte[] dhSharedSecret = Bytes.concat(new byte[][] {
/* 118 */           EllipticCurves.computeSharedSecret((ECPrivateKey)senderEphemeralKeyPair
/* 119 */             .getPrivate(), recipientECPublicKey), 
/* 120 */           EllipticCurves.computeSharedSecret(privateKey, recipientECPublicKey)
/*     */         });
/* 122 */     byte[] senderEphemeralPublicKey = EllipticCurves.pointEncode(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, ((ECPublicKey)senderEphemeralKeyPair
/*     */ 
/*     */         
/* 125 */         .getPublic()).getW());
/*     */ 
/*     */     
/* 128 */     byte[] kemSharedSecret = deriveKemSharedSecret(dhSharedSecret, senderEphemeralPublicKey, recipientPublicKey, senderPrivateKey
/*     */ 
/*     */ 
/*     */         
/* 132 */         .getSerializedPublic().toByteArray());
/* 133 */     return new HpkeKemEncapOutput(kemSharedSecret, senderEphemeralPublicKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HpkeKemEncapOutput authEncapsulate(byte[] recipientPublicKey, HpkeKemPrivateKey senderPrivateKey) throws GeneralSecurityException {
/* 140 */     KeyPair keyPair = EllipticCurves.generateKeyPair(this.curve);
/* 141 */     return authEncapsulate(recipientPublicKey, keyPair, senderPrivateKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decapsulate(byte[] encapsulatedKey, HpkeKemPrivateKey recipientPrivateKey) throws GeneralSecurityException {
/* 148 */     ECPrivateKey privateKey = EllipticCurves.getEcPrivateKey(this.curve, recipientPrivateKey
/* 149 */         .getSerializedPrivate().toByteArray());
/*     */     
/* 151 */     ECPublicKey publicKey = EllipticCurves.getEcPublicKey(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, encapsulatedKey);
/* 152 */     byte[] dhSharedSecret = EllipticCurves.computeSharedSecret(privateKey, publicKey);
/* 153 */     return deriveKemSharedSecret(dhSharedSecret, encapsulatedKey, recipientPrivateKey
/* 154 */         .getSerializedPublic().toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] authDecapsulate(byte[] encapsulatedKey, HpkeKemPrivateKey recipientPrivateKey, byte[] senderPublicKey) throws GeneralSecurityException {
/* 162 */     ECPrivateKey privateKey = EllipticCurves.getEcPrivateKey(this.curve, recipientPrivateKey
/* 163 */         .getSerializedPrivate().toByteArray());
/*     */     
/* 165 */     ECPublicKey senderEphemeralPublicKey = EllipticCurves.getEcPublicKey(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, encapsulatedKey);
/*     */ 
/*     */     
/* 168 */     byte[] dhSharedSecret = Bytes.concat(new byte[][] {
/* 169 */           EllipticCurves.computeSharedSecret(privateKey, senderEphemeralPublicKey), 
/* 170 */           EllipticCurves.computeSharedSecret(privateKey, 
/*     */             
/* 172 */             EllipticCurves.getEcPublicKey(this.curve, EllipticCurves.PointFormatType.UNCOMPRESSED, senderPublicKey))
/*     */         });
/* 174 */     return deriveKemSharedSecret(dhSharedSecret, encapsulatedKey, recipientPrivateKey
/*     */ 
/*     */         
/* 177 */         .getSerializedPublic().toByteArray(), senderPublicKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getKemId() throws GeneralSecurityException {
/* 183 */     switch (this.curve) {
/*     */       case NIST_P256:
/* 185 */         return HpkeUtil.P256_HKDF_SHA256_KEM_ID;
/*     */       case NIST_P384:
/* 187 */         return HpkeUtil.P384_HKDF_SHA384_KEM_ID;
/*     */       case NIST_P521:
/* 189 */         return HpkeUtil.P521_HKDF_SHA512_KEM_ID;
/*     */     } 
/* 191 */     throw new GeneralSecurityException("Could not determine HPKE KEM ID");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\NistCurvesHpkeKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */