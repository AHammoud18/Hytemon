/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.PrivateKeyManager;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.proto.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.proto.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.signature.internal.EcdsaProtoSerialization;
/*     */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*     */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class EcdsaSignKeyManager
/*     */ {
/*  59 */   private static final PrimitiveConstructor<EcdsaPrivateKey, PublicKeySign> PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(EcdsaSignJce::create, EcdsaPrivateKey.class, PublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final PrimitiveConstructor<EcdsaPublicKey, PublicKeyVerify> PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(EcdsaVerifyJce::create, EcdsaPublicKey.class, PublicKeyVerify.class);
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final PrivateKeyManager<PublicKeySign> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  69 */       getKeyType(), PublicKeySign.class, EcdsaPrivateKey.parser());
/*     */ 
/*     */   
/*  72 */   private static final KeyManager<PublicKeyVerify> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  73 */       EcdsaVerifyKeyManager.getKeyType(), PublicKeyVerify.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  76 */       EcdsaPublicKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  79 */     return "type.googleapis.com/google.crypto.tink.EcdsaPrivateKey";
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static EcdsaPrivateKey createKey(EcdsaParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  85 */     KeyPair keyPair = EllipticCurves.generateKeyPair(parameters.getCurveType().toParameterSpec());
/*  86 */     ECPublicKey pubKey = (ECPublicKey)keyPair.getPublic();
/*  87 */     ECPrivateKey privKey = (ECPrivateKey)keyPair.getPrivate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     EcdsaPublicKey publicKey = EcdsaPublicKey.builder().setParameters(parameters).setIdRequirement(idRequirement).setPublicPoint(pubKey.getW()).build();
/*     */     
/*  96 */     return EcdsaPrivateKey.builder()
/*  97 */       .setPublicKey(publicKey)
/*  98 */       .setPrivateValue(
/*  99 */         SecretBigInteger.fromBigInteger(privKey.getS(), InsecureSecretKeyAccess.get()))
/* 100 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 104 */   private static final KeyCreator<EcdsaParameters> KEY_CREATOR = EcdsaSignKeyManager::createKey;
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 108 */     Map<String, Parameters> result = new HashMap<>();
/* 109 */     result.put("ECDSA_P256", PredefinedSignatureParameters.ECDSA_P256);
/*     */ 
/*     */     
/* 112 */     result.put("ECDSA_P256_IEEE_P1363", PredefinedSignatureParameters.ECDSA_P256_IEEE_P1363);
/* 113 */     result.put("ECDSA_P256_RAW", 
/*     */         
/* 115 */         EcdsaParameters.builder()
/* 116 */         .setHashType(EcdsaParameters.HashType.SHA256)
/* 117 */         .setCurveType(EcdsaParameters.CurveType.NIST_P256)
/* 118 */         .setSignatureEncoding(EcdsaParameters.SignatureEncoding.IEEE_P1363)
/* 119 */         .setVariant(EcdsaParameters.Variant.NO_PREFIX)
/* 120 */         .build());
/*     */ 
/*     */     
/* 123 */     result.put("ECDSA_P256_IEEE_P1363_WITHOUT_PREFIX", PredefinedSignatureParameters.ECDSA_P256_IEEE_P1363_WITHOUT_PREFIX);
/*     */ 
/*     */     
/* 126 */     result.put("ECDSA_P384", PredefinedSignatureParameters.ECDSA_P384);
/* 127 */     result.put("ECDSA_P384_IEEE_P1363", PredefinedSignatureParameters.ECDSA_P384_IEEE_P1363);
/* 128 */     result.put("ECDSA_P384_SHA512", 
/*     */         
/* 130 */         EcdsaParameters.builder()
/* 131 */         .setHashType(EcdsaParameters.HashType.SHA512)
/* 132 */         .setCurveType(EcdsaParameters.CurveType.NIST_P384)
/* 133 */         .setSignatureEncoding(EcdsaParameters.SignatureEncoding.DER)
/* 134 */         .setVariant(EcdsaParameters.Variant.TINK)
/* 135 */         .build());
/* 136 */     result.put("ECDSA_P384_SHA384", 
/*     */         
/* 138 */         EcdsaParameters.builder()
/* 139 */         .setHashType(EcdsaParameters.HashType.SHA384)
/* 140 */         .setCurveType(EcdsaParameters.CurveType.NIST_P384)
/* 141 */         .setSignatureEncoding(EcdsaParameters.SignatureEncoding.DER)
/* 142 */         .setVariant(EcdsaParameters.Variant.TINK)
/* 143 */         .build());
/* 144 */     result.put("ECDSA_P521", PredefinedSignatureParameters.ECDSA_P521);
/* 145 */     result.put("ECDSA_P521_IEEE_P1363", PredefinedSignatureParameters.ECDSA_P521_IEEE_P1363);
/* 146 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 149 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 157 */     if (!FIPS.isCompatible()) {
/* 158 */       throw new GeneralSecurityException("Can not use ECDSA in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 161 */     EcdsaProtoSerialization.register();
/* 162 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 163 */     MutablePrimitiveRegistry.globalInstance()
/* 164 */       .registerPrimitiveConstructor(PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR);
/* 165 */     MutablePrimitiveRegistry.globalInstance()
/* 166 */       .registerPrimitiveConstructor(PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR);
/* 167 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, EcdsaParameters.class);
/* 168 */     KeyManagerRegistry.globalInstance()
/* 169 */       .registerKeyManagerWithFipsCompatibility((KeyManager)legacyPrivateKeyManager, FIPS, newKeyAllowed);
/* 170 */     KeyManagerRegistry.globalInstance()
/* 171 */       .registerKeyManagerWithFipsCompatibility(legacyPublicKeyManager, FIPS, false);
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
/*     */   public static final KeyTemplate ecdsaP256Template() {
/* 185 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(EcdsaParameters.builder().setSignatureEncoding(EcdsaParameters.SignatureEncoding.DER).setCurveType(EcdsaParameters.CurveType.NIST_P256).setHashType(EcdsaParameters.HashType.SHA256).setVariant(EcdsaParameters.Variant.TINK).build()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rawEcdsaP256Template() {
/* 209 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(EcdsaParameters.builder().setSignatureEncoding(EcdsaParameters.SignatureEncoding.IEEE_P1363).setCurveType(EcdsaParameters.CurveType.NIST_P256).setHashType(EcdsaParameters.HashType.SHA256).setVariant(EcdsaParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\EcdsaSignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */