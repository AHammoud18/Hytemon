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
/*     */ import com.google.crypto.tink.proto.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPssProtoSerialization;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssSignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssVerifyJce;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.RSAKeyGenParameterSpec;
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
/*     */ public final class RsaSsaPssSignKeyManager
/*     */ {
/*  62 */   private static final PrimitiveConstructor<RsaSsaPssPrivateKey, PublicKeySign> PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(RsaSsaPssSignJce::create, RsaSsaPssPrivateKey.class, PublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final PrimitiveConstructor<RsaSsaPssPublicKey, PublicKeyVerify> PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(RsaSsaPssVerifyJce::create, RsaSsaPssPublicKey.class, PublicKeyVerify.class);
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final PrivateKeyManager<PublicKeySign> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  72 */       getKeyType(), PublicKeySign.class, 
/*     */       
/*  74 */       RsaSsaPssPrivateKey.parser());
/*     */ 
/*     */   
/*  77 */   private static final KeyManager<PublicKeyVerify> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  78 */       RsaSsaPssVerifyKeyManager.getKeyType(), PublicKeyVerify.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  81 */       RsaSsaPssPublicKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  84 */     return "type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPssPrivateKey createKey(RsaSsaPssParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  91 */     KeyPairGenerator keyGen = (KeyPairGenerator)EngineFactory.KEY_PAIR_GENERATOR.getInstance("RSA");
/*     */ 
/*     */ 
/*     */     
/*  95 */     RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(parameters.getModulusSizeBits(), new BigInteger(1, parameters.getPublicExponent().toByteArray()));
/*  96 */     keyGen.initialize(spec);
/*  97 */     KeyPair keyPair = keyGen.generateKeyPair();
/*  98 */     RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
/*  99 */     RSAPrivateCrtKey privKey = (RSAPrivateCrtKey)keyPair.getPrivate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     RsaSsaPssPublicKey rsaSsaPssPublicKey = RsaSsaPssPublicKey.builder().setParameters(parameters).setModulus(pubKey.getModulus()).setIdRequirement(idRequirement).build();
/*     */ 
/*     */     
/* 110 */     return RsaSsaPssPrivateKey.builder()
/* 111 */       .setPublicKey(rsaSsaPssPublicKey)
/* 112 */       .setPrimes(
/* 113 */         SecretBigInteger.fromBigInteger(privKey.getPrimeP(), InsecureSecretKeyAccess.get()), 
/* 114 */         SecretBigInteger.fromBigInteger(privKey.getPrimeQ(), InsecureSecretKeyAccess.get()))
/* 115 */       .setPrivateExponent(
/* 116 */         SecretBigInteger.fromBigInteger(privKey
/* 117 */           .getPrivateExponent(), InsecureSecretKeyAccess.get()))
/* 118 */       .setPrimeExponents(
/* 119 */         SecretBigInteger.fromBigInteger(privKey
/* 120 */           .getPrimeExponentP(), InsecureSecretKeyAccess.get()), 
/* 121 */         SecretBigInteger.fromBigInteger(privKey
/* 122 */           .getPrimeExponentQ(), InsecureSecretKeyAccess.get()))
/* 123 */       .setCrtCoefficient(
/* 124 */         SecretBigInteger.fromBigInteger(privKey
/* 125 */           .getCrtCoefficient(), InsecureSecretKeyAccess.get()))
/* 126 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 130 */   private static final KeyCreator<RsaSsaPssParameters> KEY_CREATOR = RsaSsaPssSignKeyManager::createKey;
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 134 */     Map<String, Parameters> result = new HashMap<>();
/* 135 */     result.put("RSA_SSA_PSS_3072_SHA256_F4", 
/*     */         
/* 137 */         RsaSsaPssParameters.builder()
/* 138 */         .setSigHashType(RsaSsaPssParameters.HashType.SHA256)
/* 139 */         .setMgf1HashType(RsaSsaPssParameters.HashType.SHA256)
/* 140 */         .setSaltLengthBytes(32)
/* 141 */         .setModulusSizeBits(3072)
/* 142 */         .setPublicExponent(RsaSsaPssParameters.F4)
/* 143 */         .setVariant(RsaSsaPssParameters.Variant.TINK)
/* 144 */         .build());
/* 145 */     result.put("RSA_SSA_PSS_3072_SHA256_F4_RAW", 
/*     */         
/* 147 */         RsaSsaPssParameters.builder()
/* 148 */         .setSigHashType(RsaSsaPssParameters.HashType.SHA256)
/* 149 */         .setMgf1HashType(RsaSsaPssParameters.HashType.SHA256)
/* 150 */         .setSaltLengthBytes(32)
/* 151 */         .setModulusSizeBits(3072)
/* 152 */         .setPublicExponent(RsaSsaPssParameters.F4)
/* 153 */         .setVariant(RsaSsaPssParameters.Variant.NO_PREFIX)
/* 154 */         .build());
/*     */ 
/*     */     
/* 157 */     result.put("RSA_SSA_PSS_3072_SHA256_SHA256_32_F4", PredefinedSignatureParameters.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4);
/*     */ 
/*     */     
/* 160 */     result.put("RSA_SSA_PSS_4096_SHA512_F4", 
/*     */         
/* 162 */         RsaSsaPssParameters.builder()
/* 163 */         .setSigHashType(RsaSsaPssParameters.HashType.SHA512)
/* 164 */         .setMgf1HashType(RsaSsaPssParameters.HashType.SHA512)
/* 165 */         .setSaltLengthBytes(64)
/* 166 */         .setModulusSizeBits(4096)
/* 167 */         .setPublicExponent(RsaSsaPssParameters.F4)
/* 168 */         .setVariant(RsaSsaPssParameters.Variant.TINK)
/* 169 */         .build());
/* 170 */     result.put("RSA_SSA_PSS_4096_SHA512_F4_RAW", 
/*     */         
/* 172 */         RsaSsaPssParameters.builder()
/* 173 */         .setSigHashType(RsaSsaPssParameters.HashType.SHA512)
/* 174 */         .setMgf1HashType(RsaSsaPssParameters.HashType.SHA512)
/* 175 */         .setSaltLengthBytes(64)
/* 176 */         .setModulusSizeBits(4096)
/* 177 */         .setPublicExponent(RsaSsaPssParameters.F4)
/* 178 */         .setVariant(RsaSsaPssParameters.Variant.NO_PREFIX)
/* 179 */         .build());
/*     */ 
/*     */     
/* 182 */     result.put("RSA_SSA_PSS_4096_SHA512_SHA512_64_F4", PredefinedSignatureParameters.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4);
/*     */ 
/*     */     
/* 185 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 188 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 196 */     if (!FIPS.isCompatible()) {
/* 197 */       throw new GeneralSecurityException("Can not use RSA SSA PSS in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 200 */     RsaSsaPssProtoSerialization.register();
/* 201 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 202 */     MutablePrimitiveRegistry.globalInstance()
/* 203 */       .registerPrimitiveConstructor(PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR);
/* 204 */     MutablePrimitiveRegistry.globalInstance()
/* 205 */       .registerPrimitiveConstructor(PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR);
/* 206 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, RsaSsaPssParameters.class);
/* 207 */     KeyManagerRegistry.globalInstance()
/* 208 */       .registerKeyManagerWithFipsCompatibility((KeyManager)legacyPrivateKeyManager, FIPS, newKeyAllowed);
/* 209 */     KeyManagerRegistry.globalInstance()
/* 210 */       .registerKeyManagerWithFipsCompatibility(legacyPublicKeyManager, FIPS, false);
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
/*     */   public static final KeyTemplate rsa3072PssSha256F4Template() {
/* 226 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPssParameters.builder().setSigHashType(RsaSsaPssParameters.HashType.SHA256).setMgf1HashType(RsaSsaPssParameters.HashType.SHA256).setSaltLengthBytes(32).setModulusSizeBits(3072).setPublicExponent(RsaSsaPssParameters.F4).setVariant(RsaSsaPssParameters.Variant.TINK).build()));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rawRsa3072PssSha256F4Template() {
/* 253 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPssParameters.builder().setSigHashType(RsaSsaPssParameters.HashType.SHA256).setMgf1HashType(RsaSsaPssParameters.HashType.SHA256).setSaltLengthBytes(32).setModulusSizeBits(3072).setPublicExponent(RsaSsaPssParameters.F4).setVariant(RsaSsaPssParameters.Variant.NO_PREFIX).build()));
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
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rsa4096PssSha512F4Template() {
/* 279 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPssParameters.builder().setSigHashType(RsaSsaPssParameters.HashType.SHA512).setMgf1HashType(RsaSsaPssParameters.HashType.SHA512).setSaltLengthBytes(64).setModulusSizeBits(4096).setPublicExponent(RsaSsaPssParameters.F4).setVariant(RsaSsaPssParameters.Variant.TINK).build()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rawRsa4096PssSha512F4Template() {
/* 307 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(RsaSsaPssParameters.builder().setSigHashType(RsaSsaPssParameters.HashType.SHA512).setMgf1HashType(RsaSsaPssParameters.HashType.SHA512).setSaltLengthBytes(64).setModulusSizeBits(4096).setPublicExponent(RsaSsaPssParameters.F4).setVariant(RsaSsaPssParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPssSignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */