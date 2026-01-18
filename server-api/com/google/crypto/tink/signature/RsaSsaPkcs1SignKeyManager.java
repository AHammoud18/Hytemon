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
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPkcs1ProtoSerialization;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.RSAKeyGenParameterSpec;
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
/*     */ public final class RsaSsaPkcs1SignKeyManager
/*     */ {
/*  61 */   private static final PrimitiveConstructor<RsaSsaPkcs1PrivateKey, PublicKeySign> PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(RsaSsaPkcs1SignJce::create, RsaSsaPkcs1PrivateKey.class, PublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final PrimitiveConstructor<RsaSsaPkcs1PublicKey, PublicKeyVerify> PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(RsaSsaPkcs1VerifyJce::create, RsaSsaPkcs1PublicKey.class, PublicKeyVerify.class);
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final PrivateKeyManager<PublicKeySign> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  71 */       getKeyType(), PublicKeySign.class, 
/*     */       
/*  73 */       RsaSsaPkcs1PrivateKey.parser());
/*     */ 
/*     */   
/*  76 */   private static final KeyManager<PublicKeyVerify> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  77 */       RsaSsaPkcs1VerifyKeyManager.getKeyType(), PublicKeyVerify.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  80 */       RsaSsaPkcs1PublicKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  83 */     return "type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPkcs1PrivateKey createKey(RsaSsaPkcs1Parameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  90 */     KeyPairGenerator keyGen = (KeyPairGenerator)EngineFactory.KEY_PAIR_GENERATOR.getInstance("RSA");
/*     */ 
/*     */ 
/*     */     
/*  94 */     RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(parameters.getModulusSizeBits(), new BigInteger(1, parameters.getPublicExponent().toByteArray()));
/*  95 */     keyGen.initialize(spec);
/*  96 */     KeyPair keyPair = keyGen.generateKeyPair();
/*  97 */     RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
/*  98 */     RSAPrivateCrtKey privKey = (RSAPrivateCrtKey)keyPair.getPrivate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     RsaSsaPkcs1PublicKey rsaSsaPkcs1PublicKey = RsaSsaPkcs1PublicKey.builder().setParameters(parameters).setModulus(pubKey.getModulus()).setIdRequirement(idRequirement).build();
/*     */ 
/*     */     
/* 109 */     return RsaSsaPkcs1PrivateKey.builder()
/* 110 */       .setPublicKey(rsaSsaPkcs1PublicKey)
/* 111 */       .setPrimes(
/* 112 */         SecretBigInteger.fromBigInteger(privKey.getPrimeP(), InsecureSecretKeyAccess.get()), 
/* 113 */         SecretBigInteger.fromBigInteger(privKey.getPrimeQ(), InsecureSecretKeyAccess.get()))
/* 114 */       .setPrivateExponent(
/* 115 */         SecretBigInteger.fromBigInteger(privKey
/* 116 */           .getPrivateExponent(), InsecureSecretKeyAccess.get()))
/* 117 */       .setPrimeExponents(
/* 118 */         SecretBigInteger.fromBigInteger(privKey
/* 119 */           .getPrimeExponentP(), InsecureSecretKeyAccess.get()), 
/* 120 */         SecretBigInteger.fromBigInteger(privKey
/* 121 */           .getPrimeExponentQ(), InsecureSecretKeyAccess.get()))
/* 122 */       .setCrtCoefficient(
/* 123 */         SecretBigInteger.fromBigInteger(privKey
/* 124 */           .getCrtCoefficient(), InsecureSecretKeyAccess.get()))
/* 125 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 129 */   private static final KeyCreator<RsaSsaPkcs1Parameters> KEY_CREATOR = RsaSsaPkcs1SignKeyManager::createKey;
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 133 */     Map<String, Parameters> result = new HashMap<>();
/* 134 */     result.put("RSA_SSA_PKCS1_3072_SHA256_F4", PredefinedSignatureParameters.RSA_SSA_PKCS1_3072_SHA256_F4);
/*     */     
/* 136 */     result.put("RSA_SSA_PKCS1_3072_SHA256_F4_RAW", 
/*     */         
/* 138 */         RsaSsaPkcs1Parameters.builder()
/* 139 */         .setHashType(RsaSsaPkcs1Parameters.HashType.SHA256)
/* 140 */         .setModulusSizeBits(3072)
/* 141 */         .setPublicExponent(RsaSsaPkcs1Parameters.F4)
/* 142 */         .setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX)
/* 143 */         .build());
/*     */ 
/*     */     
/* 146 */     result.put("RSA_SSA_PKCS1_3072_SHA256_F4_WITHOUT_PREFIX", PredefinedSignatureParameters.RSA_SSA_PKCS1_3072_SHA256_F4_WITHOUT_PREFIX);
/*     */ 
/*     */     
/* 149 */     result.put("RSA_SSA_PKCS1_4096_SHA512_F4", PredefinedSignatureParameters.RSA_SSA_PKCS1_4096_SHA512_F4);
/*     */     
/* 151 */     result.put("RSA_SSA_PKCS1_4096_SHA512_F4_RAW", 
/*     */         
/* 153 */         RsaSsaPkcs1Parameters.builder()
/* 154 */         .setHashType(RsaSsaPkcs1Parameters.HashType.SHA512)
/* 155 */         .setModulusSizeBits(4096)
/* 156 */         .setPublicExponent(RsaSsaPkcs1Parameters.F4)
/* 157 */         .setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX)
/* 158 */         .build());
/* 159 */     return result;
/*     */   }
/*     */   
/* 162 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 170 */     if (!FIPS.isCompatible()) {
/* 171 */       throw new GeneralSecurityException("Can not use RSA SSA PKCS1 in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 174 */     RsaSsaPkcs1ProtoSerialization.register();
/* 175 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 176 */     MutablePrimitiveRegistry.globalInstance()
/* 177 */       .registerPrimitiveConstructor(PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR);
/* 178 */     MutablePrimitiveRegistry.globalInstance()
/* 179 */       .registerPrimitiveConstructor(PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR);
/* 180 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, RsaSsaPkcs1Parameters.class);
/* 181 */     KeyManagerRegistry.globalInstance()
/* 182 */       .registerKeyManagerWithFipsCompatibility((KeyManager)legacyPrivateKeyManager, FIPS, newKeyAllowed);
/* 183 */     KeyManagerRegistry.globalInstance()
/* 184 */       .registerKeyManagerWithFipsCompatibility(legacyPublicKeyManager, FIPS, false);
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
/*     */   public static final KeyTemplate rsa3072SsaPkcs1Sha256F4Template() {
/* 198 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPkcs1Parameters.builder().setModulusSizeBits(3072).setPublicExponent(RsaSsaPkcs1Parameters.F4).setHashType(RsaSsaPkcs1Parameters.HashType.SHA256).setVariant(RsaSsaPkcs1Parameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawRsa3072SsaPkcs1Sha256F4Template() {
/* 220 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPkcs1Parameters.builder().setModulusSizeBits(3072).setPublicExponent(RsaSsaPkcs1Parameters.F4).setHashType(RsaSsaPkcs1Parameters.HashType.SHA256).setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX).build()));
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
/*     */   public static final KeyTemplate rsa4096SsaPkcs1Sha512F4Template() {
/* 242 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPkcs1Parameters.builder().setModulusSizeBits(4096).setPublicExponent(RsaSsaPkcs1Parameters.F4).setHashType(RsaSsaPkcs1Parameters.HashType.SHA512).setVariant(RsaSsaPkcs1Parameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawRsa4096SsaPkcs1Sha512F4Template() {
/* 264 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPkcs1Parameters.builder().setModulusSizeBits(4096).setPublicExponent(RsaSsaPkcs1Parameters.F4).setHashType(RsaSsaPkcs1Parameters.HashType.SHA512).setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPkcs1SignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */