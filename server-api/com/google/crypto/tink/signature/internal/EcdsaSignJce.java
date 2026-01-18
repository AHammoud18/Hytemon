/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.signature.EcdsaParameters;
/*     */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.Enums;
/*     */ import com.google.crypto.tink.subtle.SubtleUtil;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPrivateKeySpec;
/*     */ import java.security.spec.EllipticCurve;
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
/*     */ @Immutable
/*     */ public final class EcdsaSignJce
/*     */   implements PublicKeySign
/*     */ {
/*  51 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  54 */   private static final byte[] EMPTY = new byte[0];
/*  55 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */ 
/*     */ 
/*     */   
/*     */   private final ECPrivateKey privateKey;
/*     */ 
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
/*     */   
/*     */   @Nullable
/*     */   private final Provider provider;
/*     */ 
/*     */ 
/*     */   
/*     */   private EcdsaSignJce(ECPrivateKey privateKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding, byte[] outputPrefix, byte[] messageSuffix, Provider provider) throws GeneralSecurityException {
/*  81 */     if (!FIPS.isCompatible()) {
/*  82 */       throw new GeneralSecurityException("Can not use ECDSA in FIPS-mode, as BoringCrypto is not available.");
/*     */     }
/*     */ 
/*     */     
/*  86 */     this.privateKey = privateKey;
/*  87 */     this.signatureAlgorithm = SubtleUtil.toEcdsaAlgo(hash);
/*  88 */     this.encoding = encoding;
/*  89 */     this.outputPrefix = outputPrefix;
/*  90 */     this.messageSuffix = messageSuffix;
/*  91 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaSignJce(ECPrivateKey privateKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding) throws GeneralSecurityException {
/*  96 */     this(privateKey, hash, encoding, EMPTY, EMPTY, ConscryptUtil.providerOrNull());
/*     */   }
/*     */   
/*     */   public static PublicKeySign create(EcdsaPrivateKey key) throws GeneralSecurityException {
/* 100 */     Provider provider = ConscryptUtil.providerOrNull();
/* 101 */     return createWithProviderOrNull(key, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKeySign createWithProvider(EcdsaPrivateKey key, Provider provider) throws GeneralSecurityException {
/* 110 */     if (provider == null) {
/* 111 */       throw new NullPointerException("provider must not be null");
/*     */     }
/* 113 */     return createWithProviderOrNull(key, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static PublicKeySign createWithProviderOrNull(EcdsaPrivateKey key, @Nullable Provider provider) throws GeneralSecurityException {
/*     */     KeyFactory keyFactory;
/* 120 */     Enums.HashType hashType = (Enums.HashType)EcdsaVerifyJce.HASH_TYPE_CONVERTER.toProtoEnum(key.getParameters().getHashType());
/*     */     
/* 122 */     EllipticCurves.EcdsaEncoding ecdsaEncoding = (EllipticCurves.EcdsaEncoding)EcdsaVerifyJce.ENCODING_CONVERTER.toProtoEnum(key.getParameters().getSignatureEncoding());
/*     */     
/* 124 */     EllipticCurves.CurveType curveType = (EllipticCurves.CurveType)EcdsaVerifyJce.CURVE_TYPE_CONVERTER.toProtoEnum(key.getParameters().getCurveType());
/* 125 */     ECParameterSpec ecParams = EllipticCurves.getCurveSpec(curveType);
/*     */ 
/*     */     
/* 128 */     ECPrivateKeySpec spec = new ECPrivateKeySpec(key.getPrivateValue().getBigInteger(InsecureSecretKeyAccess.get()), ecParams);
/*     */     
/* 130 */     if (provider != null) {
/* 131 */       keyFactory = KeyFactory.getInstance("EC", provider);
/*     */     } else {
/* 133 */       keyFactory = (KeyFactory)EngineFactory.KEY_FACTORY.getInstance("EC");
/*     */     } 
/* 135 */     ECPrivateKey privateKey = (ECPrivateKey)keyFactory.generatePrivate(spec);
/*     */     
/* 137 */     return new EcdsaSignJce(privateKey, hashType, ecdsaEncoding, key
/*     */ 
/*     */ 
/*     */         
/* 141 */         .getOutputPrefix().toByteArray(), 
/* 142 */         key.getParameters().getVariant().equals(EcdsaParameters.Variant.LEGACY) ? 
/* 143 */         legacyMessageSuffix : 
/* 144 */         EMPTY, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   private Signature getInstance(String signatureAlgorithm) throws GeneralSecurityException {
/* 149 */     if (this.provider != null) {
/* 150 */       return Signature.getInstance(signatureAlgorithm, this.provider);
/*     */     }
/* 152 */     return (Signature)EngineFactory.SIGNATURE.getInstance(signatureAlgorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 157 */     Signature signer = getInstance(this.signatureAlgorithm);
/* 158 */     signer.initSign(this.privateKey);
/* 159 */     signer.update(data);
/* 160 */     if (this.messageSuffix.length > 0) {
/* 161 */       signer.update(this.messageSuffix);
/*     */     }
/* 163 */     byte[] signature = signer.sign();
/* 164 */     if (this.encoding == EllipticCurves.EcdsaEncoding.IEEE_P1363) {
/* 165 */       EllipticCurve curve = this.privateKey.getParams().getCurve();
/*     */       
/* 167 */       signature = EllipticCurves.ecdsaDer2Ieee(signature, 2 * EllipticCurves.fieldSizeInBytes(curve));
/*     */     } 
/* 169 */     if (this.outputPrefix.length == 0) {
/* 170 */       return signature;
/*     */     }
/* 172 */     return Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\EcdsaSignJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */