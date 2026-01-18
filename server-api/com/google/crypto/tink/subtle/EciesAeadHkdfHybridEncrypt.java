/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.HybridEncrypt;
/*     */ import com.google.crypto.tink.hybrid.EciesParameters;
/*     */ import com.google.crypto.tink.hybrid.EciesPublicKey;
/*     */ import com.google.crypto.tink.hybrid.internal.EciesDemHelper;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EciesAeadHkdfHybridEncrypt
/*     */   implements HybridEncrypt
/*     */ {
/*     */   private final EciesHkdfSenderKem senderKem;
/*     */   private final String hkdfHmacAlgo;
/*     */   private final byte[] hkdfSalt;
/*     */   private final EllipticCurves.PointFormatType ecPointFormat;
/*     */   private final EciesDemHelper.Dem dem;
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   static final String toHmacAlgo(EciesParameters.HashType hash) throws GeneralSecurityException {
/*  44 */     if (hash.equals(EciesParameters.HashType.SHA1)) {
/*  45 */       return "HmacSha1";
/*     */     }
/*  47 */     if (hash.equals(EciesParameters.HashType.SHA224)) {
/*  48 */       return "HmacSha224";
/*     */     }
/*  50 */     if (hash.equals(EciesParameters.HashType.SHA256)) {
/*  51 */       return "HmacSha256";
/*     */     }
/*  53 */     if (hash.equals(EciesParameters.HashType.SHA384)) {
/*  54 */       return "HmacSha384";
/*     */     }
/*  56 */     if (hash.equals(EciesParameters.HashType.SHA512)) {
/*  57 */       return "HmacSha512";
/*     */     }
/*  59 */     throw new GeneralSecurityException("hash unsupported for EciesAeadHkdf: " + hash);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  64 */   static final EnumTypeProtoConverter<EllipticCurves.CurveType, EciesParameters.CurveType> CURVE_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  65 */     .add(EllipticCurves.CurveType.NIST_P256, EciesParameters.CurveType.NIST_P256)
/*  66 */     .add(EllipticCurves.CurveType.NIST_P384, EciesParameters.CurveType.NIST_P384)
/*  67 */     .add(EllipticCurves.CurveType.NIST_P521, EciesParameters.CurveType.NIST_P521)
/*  68 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   static final EnumTypeProtoConverter<EllipticCurves.PointFormatType, EciesParameters.PointFormat> POINT_FORMAT_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  74 */     .add(EllipticCurves.PointFormatType.UNCOMPRESSED, EciesParameters.PointFormat.UNCOMPRESSED)
/*  75 */     .add(EllipticCurves.PointFormatType.COMPRESSED, EciesParameters.PointFormat.COMPRESSED)
/*  76 */     .add(EllipticCurves.PointFormatType.DO_NOT_USE_CRUNCHY_UNCOMPRESSED, EciesParameters.PointFormat.LEGACY_UNCOMPRESSED)
/*     */ 
/*     */     
/*  79 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EciesAeadHkdfHybridEncrypt(ECPublicKey recipientPublicKey, byte[] hkdfSalt, String hkdfHmacAlgo, EllipticCurves.PointFormatType ecPointFormat, EciesDemHelper.Dem dem, byte[] outputPrefix) throws GeneralSecurityException {
/*  89 */     EllipticCurves.checkPublicKey(recipientPublicKey);
/*  90 */     this.senderKem = new EciesHkdfSenderKem(recipientPublicKey);
/*  91 */     this.hkdfSalt = hkdfSalt;
/*  92 */     this.hkdfHmacAlgo = hkdfHmacAlgo;
/*  93 */     this.ecPointFormat = ecPointFormat;
/*  94 */     this.dem = dem;
/*  95 */     this.outputPrefix = outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static HybridEncrypt create(EciesPublicKey key) throws GeneralSecurityException {
/* 101 */     EllipticCurves.CurveType curveType = (EllipticCurves.CurveType)CURVE_TYPE_CONVERTER.toProtoEnum(key.getParameters().getCurveType());
/*     */     
/* 103 */     ECPublicKey recipientPublicKey = EllipticCurves.getEcPublicKey(curveType, key
/*     */         
/* 105 */         .getNistCurvePoint().getAffineX().toByteArray(), key
/* 106 */         .getNistCurvePoint().getAffineY().toByteArray());
/* 107 */     byte[] hkdfSalt = new byte[0];
/* 108 */     if (key.getParameters().getSalt() != null) {
/* 109 */       hkdfSalt = key.getParameters().getSalt().toByteArray();
/*     */     }
/* 111 */     return new EciesAeadHkdfHybridEncrypt(recipientPublicKey, hkdfSalt, 
/*     */ 
/*     */         
/* 114 */         toHmacAlgo(key.getParameters().getHashType()), (EllipticCurves.PointFormatType)POINT_FORMAT_TYPE_CONVERTER
/* 115 */         .toProtoEnum(key.getParameters().getNistCurvePointFormat()), 
/* 116 */         EciesDemHelper.getDem(key.getParameters()), key
/* 117 */         .getOutputPrefix().toByteArray());
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
/*     */   public byte[] encrypt(byte[] plaintext, byte[] contextInfo) throws GeneralSecurityException {
/* 130 */     EciesHkdfSenderKem.KemKey kemKey = this.senderKem.generateKey(this.hkdfHmacAlgo, this.hkdfSalt, contextInfo, this.dem
/* 131 */         .getSymmetricKeySizeInBytes(), this.ecPointFormat);
/* 132 */     return this.dem.encrypt(kemKey.getSymmetricKey(), this.outputPrefix, kemKey.getKemBytes(), plaintext);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EciesAeadHkdfHybridEncrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */