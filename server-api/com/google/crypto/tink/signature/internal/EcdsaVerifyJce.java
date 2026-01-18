/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.EcdsaParameters;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.Enums;
/*     */ import com.google.crypto.tink.subtle.SubtleUtil;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.security.spec.ECPublicKeySpec;
/*     */ import java.security.spec.EllipticCurve;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class EcdsaVerifyJce
/*     */   implements PublicKeyVerify
/*     */ {
/*  55 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  58 */   private static final byte[] EMPTY = new byte[0];
/*  59 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */ 
/*     */   
/*     */   private final ECPublicKey publicKey;
/*     */ 
/*     */   
/*     */   private final String signatureAlgorithm;
/*     */ 
/*     */   
/*     */   private final EllipticCurves.EcdsaEncoding encoding;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */   
/*     */   @Nullable
/*     */   private final Provider provider;
/*     */   
/*  79 */   static final EnumTypeProtoConverter<Enums.HashType, EcdsaParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  80 */     .add((Enum)Enums.HashType.SHA256, EcdsaParameters.HashType.SHA256)
/*  81 */     .add((Enum)Enums.HashType.SHA384, EcdsaParameters.HashType.SHA384)
/*  82 */     .add((Enum)Enums.HashType.SHA512, EcdsaParameters.HashType.SHA512)
/*  83 */     .build();
/*     */ 
/*     */   
/*  86 */   static final EnumTypeProtoConverter<EllipticCurves.EcdsaEncoding, EcdsaParameters.SignatureEncoding> ENCODING_CONVERTER = EnumTypeProtoConverter.builder()
/*  87 */     .add((Enum)EllipticCurves.EcdsaEncoding.IEEE_P1363, EcdsaParameters.SignatureEncoding.IEEE_P1363)
/*  88 */     .add((Enum)EllipticCurves.EcdsaEncoding.DER, EcdsaParameters.SignatureEncoding.DER)
/*  89 */     .build();
/*     */   
/*  91 */   static final EnumTypeProtoConverter<EllipticCurves.CurveType, EcdsaParameters.CurveType> CURVE_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  92 */     .add((Enum)EllipticCurves.CurveType.NIST_P256, EcdsaParameters.CurveType.NIST_P256)
/*  93 */     .add((Enum)EllipticCurves.CurveType.NIST_P384, EcdsaParameters.CurveType.NIST_P384)
/*  94 */     .add((Enum)EllipticCurves.CurveType.NIST_P521, EcdsaParameters.CurveType.NIST_P521)
/*  95 */     .build();
/*     */   
/*     */   public static PublicKeyVerify create(EcdsaPublicKey key) throws GeneralSecurityException {
/*  98 */     Provider provider = ConscryptUtil.providerOrNull();
/*  99 */     return createWithProviderOrNull(key, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKeyVerify createWithProvider(EcdsaPublicKey key, Provider provider) throws GeneralSecurityException {
/* 108 */     if (provider == null) {
/* 109 */       throw new NullPointerException("provider must not be null");
/*     */     }
/* 111 */     return createWithProviderOrNull(key, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify createWithProviderOrNull(EcdsaPublicKey key, @Nullable Provider provider) throws GeneralSecurityException {
/*     */     KeyFactory keyFactory;
/* 118 */     ECParameterSpec ecParams = EllipticCurves.getCurveSpec((EllipticCurves.CurveType)CURVE_TYPE_CONVERTER
/* 119 */         .toProtoEnum(key.getParameters().getCurveType()));
/* 120 */     ECPoint publicPoint = key.getPublicPoint();
/* 121 */     ECPublicKeySpec spec = new ECPublicKeySpec(publicPoint, ecParams);
/*     */     
/* 123 */     if (provider != null) {
/* 124 */       keyFactory = KeyFactory.getInstance("EC", provider);
/*     */     } else {
/* 126 */       keyFactory = (KeyFactory)EngineFactory.KEY_FACTORY.getInstance("EC");
/*     */     } 
/* 128 */     ECPublicKey publicKey = (ECPublicKey)keyFactory.generatePublic(spec);
/*     */     
/* 130 */     return new EcdsaVerifyJce(publicKey, (Enums.HashType)HASH_TYPE_CONVERTER
/*     */         
/* 132 */         .toProtoEnum(key.getParameters().getHashType()), (EllipticCurves.EcdsaEncoding)ENCODING_CONVERTER
/* 133 */         .toProtoEnum(key.getParameters().getSignatureEncoding()), key
/* 134 */         .getOutputPrefix().toByteArray(), 
/* 135 */         key.getParameters().getVariant().equals(EcdsaParameters.Variant.LEGACY) ? 
/* 136 */         legacyMessageSuffix : 
/* 137 */         EMPTY, provider);
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
/*     */   private EcdsaVerifyJce(ECPublicKey publicKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding, byte[] outputPrefix, byte[] messageSuffix, Provider provider) throws GeneralSecurityException {
/* 149 */     if (!FIPS.isCompatible()) {
/* 150 */       throw new GeneralSecurityException("Can not use ECDSA in FIPS-mode, as BoringCrypto is not available.");
/*     */     }
/*     */ 
/*     */     
/* 154 */     this.signatureAlgorithm = SubtleUtil.toEcdsaAlgo(hash);
/* 155 */     this.publicKey = publicKey;
/* 156 */     this.encoding = encoding;
/* 157 */     this.outputPrefix = outputPrefix;
/* 158 */     this.messageSuffix = messageSuffix;
/* 159 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaVerifyJce(ECPublicKey publicKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding) throws GeneralSecurityException {
/* 164 */     this(publicKey, hash, encoding, EMPTY, EMPTY, ConscryptUtil.providerOrNull());
/* 165 */     EllipticCurvesUtil.checkPointOnCurve(publicKey.getW(), publicKey.getParams().getCurve());
/*     */   }
/*     */   
/*     */   private Signature getInstance(String signatureAlgorithm) throws GeneralSecurityException {
/* 169 */     if (this.provider != null) {
/* 170 */       return Signature.getInstance(signatureAlgorithm, this.provider);
/*     */     }
/* 172 */     return (Signature)EngineFactory.SIGNATURE.getInstance(signatureAlgorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   private void noPrefixVerify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 177 */     byte[] derSignature = signature;
/* 178 */     if (this.encoding == EllipticCurves.EcdsaEncoding.IEEE_P1363) {
/* 179 */       EllipticCurve curve = this.publicKey.getParams().getCurve();
/* 180 */       if (signature.length != 2 * EllipticCurves.fieldSizeInBytes(curve)) {
/* 181 */         throw new GeneralSecurityException("Invalid signature");
/*     */       }
/* 183 */       derSignature = EllipticCurves.ecdsaIeee2Der(signature);
/*     */     } 
/* 185 */     if (!EllipticCurves.isValidDerEncoding(derSignature)) {
/* 186 */       throw new GeneralSecurityException("Invalid signature");
/*     */     }
/* 188 */     Signature verifier = getInstance(this.signatureAlgorithm);
/* 189 */     verifier.initVerify(this.publicKey);
/* 190 */     verifier.update(data);
/* 191 */     if (this.messageSuffix.length > 0) {
/* 192 */       verifier.update(this.messageSuffix);
/*     */     }
/* 194 */     boolean verified = false;
/*     */     try {
/* 196 */       verified = verifier.verify(derSignature);
/* 197 */     } catch (RuntimeException ex) {
/* 198 */       verified = false;
/*     */     } 
/* 200 */     if (!verified) {
/* 201 */       throw new GeneralSecurityException("Invalid signature");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 207 */     if (this.outputPrefix.length == 0) {
/* 208 */       noPrefixVerify(signature, data);
/*     */       return;
/*     */     } 
/* 211 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 212 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 214 */     byte[] signatureNoPrefix = Arrays.copyOfRange(signature, this.outputPrefix.length, signature.length);
/* 215 */     noPrefixVerify(signatureNoPrefix, data);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\EcdsaVerifyJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */