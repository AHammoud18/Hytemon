/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.PrivateKeyManager;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.jwt.internal.JwtEcdsaProtoSerialization;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaPrivateKey;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaPublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import java.nio.charset.StandardCharsets;
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
/*     */ public final class JwtEcdsaSignKeyManager
/*     */ {
/*  55 */   private static final PrivateKeyManager<Void> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  56 */       getKeyType(), Void.class, JwtEcdsaPrivateKey.parser());
/*     */ 
/*     */   
/*  59 */   private static final KeyManager<Void> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  60 */       JwtEcdsaVerifyKeyManager.getKeyType(), Void.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  63 */       JwtEcdsaPublicKey.parser());
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static EcdsaPrivateKey toEcdsaPrivateKey(JwtEcdsaPrivateKey privateKey) throws GeneralSecurityException {
/*  68 */     return privateKey.getEcdsaPrivateKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static JwtPublicKeySign createFullPrimitive(final JwtEcdsaPrivateKey privateKey) throws GeneralSecurityException {
/*  74 */     EcdsaPrivateKey ecdsaPrivateKey = toEcdsaPrivateKey(privateKey);
/*  75 */     final PublicKeySign signer = EcdsaSignJce.create(ecdsaPrivateKey);
/*  76 */     final String algorithm = privateKey.getParameters().getAlgorithm().getStandardName();
/*  77 */     return new JwtPublicKeySign()
/*     */       {
/*     */         public String signAndEncode(RawJwt rawJwt) throws GeneralSecurityException
/*     */         {
/*  81 */           String unsignedCompact = JwtFormat.createUnsignedCompact(algorithm, privateKey.getPublicKey().getKid(), rawJwt);
/*  82 */           return JwtFormat.createSignedCompact(unsignedCompact, signer
/*  83 */               .sign(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private static final PrimitiveConstructor<JwtEcdsaPrivateKey, JwtPublicKeySign> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtEcdsaSignKeyManager::createFullPrimitive, JwtEcdsaPrivateKey.class, JwtPublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtEcdsaPrivateKey createKey(JwtEcdsaParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 101 */     KeyPair keyPair = EllipticCurves.generateKeyPair(parameters.getAlgorithm().getEcParameterSpec());
/* 102 */     ECPublicKey pubKey = (ECPublicKey)keyPair.getPublic();
/* 103 */     ECPrivateKey privKey = (ECPrivateKey)keyPair.getPrivate();
/*     */ 
/*     */     
/* 106 */     JwtEcdsaPublicKey.Builder publicKeyBuilder = JwtEcdsaPublicKey.builder().setParameters(parameters).setPublicPoint(pubKey.getW());
/* 107 */     if (idRequirement != null) {
/* 108 */       publicKeyBuilder.setIdRequirement(idRequirement);
/*     */     }
/* 110 */     return JwtEcdsaPrivateKey.create(publicKeyBuilder
/* 111 */         .build(), 
/* 112 */         SecretBigInteger.fromBigInteger(privKey.getS(), InsecureSecretKeyAccess.get()));
/*     */   }
/*     */ 
/*     */   
/* 116 */   private static final KeyCreator<JwtEcdsaParameters> KEY_CREATOR = JwtEcdsaSignKeyManager::createKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/* 122 */     return "type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 130 */     Map<String, Parameters> result = new HashMap<>();
/* 131 */     result.put("JWT_ES256_RAW", 
/*     */         
/* 133 */         JwtEcdsaParameters.builder()
/* 134 */         .setAlgorithm(JwtEcdsaParameters.Algorithm.ES256)
/* 135 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.IGNORED)
/* 136 */         .build());
/* 137 */     result.put("JWT_ES256", 
/*     */         
/* 139 */         JwtEcdsaParameters.builder()
/* 140 */         .setAlgorithm(JwtEcdsaParameters.Algorithm.ES256)
/* 141 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 142 */         .build());
/* 143 */     result.put("JWT_ES384_RAW", 
/*     */         
/* 145 */         JwtEcdsaParameters.builder()
/* 146 */         .setAlgorithm(JwtEcdsaParameters.Algorithm.ES384)
/* 147 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.IGNORED)
/* 148 */         .build());
/* 149 */     result.put("JWT_ES384", 
/*     */         
/* 151 */         JwtEcdsaParameters.builder()
/* 152 */         .setAlgorithm(JwtEcdsaParameters.Algorithm.ES384)
/* 153 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 154 */         .build());
/* 155 */     result.put("JWT_ES512_RAW", 
/*     */         
/* 157 */         JwtEcdsaParameters.builder()
/* 158 */         .setAlgorithm(JwtEcdsaParameters.Algorithm.ES512)
/* 159 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.IGNORED)
/* 160 */         .build());
/* 161 */     result.put("JWT_ES512", 
/*     */         
/* 163 */         JwtEcdsaParameters.builder()
/* 164 */         .setAlgorithm(JwtEcdsaParameters.Algorithm.ES512)
/* 165 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 166 */         .build());
/* 167 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 170 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 178 */     if (!FIPS.isCompatible()) {
/* 179 */       throw new GeneralSecurityException("Can not use ECDSA in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 182 */     KeyManagerRegistry.globalInstance()
/* 183 */       .registerKeyManagerWithFipsCompatibility((KeyManager)legacyPrivateKeyManager, FIPS, newKeyAllowed);
/* 184 */     KeyManagerRegistry.globalInstance()
/* 185 */       .registerKeyManagerWithFipsCompatibility(legacyPublicKeyManager, FIPS, false);
/* 186 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, JwtEcdsaParameters.class);
/* 187 */     JwtEcdsaProtoSerialization.register();
/* 188 */     MutablePrimitiveRegistry.globalInstance()
/* 189 */       .registerPrimitiveConstructor(JwtEcdsaVerifyKeyManager.PRIMITIVE_CONSTRUCTOR);
/* 190 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(PRIMITIVE_CONSTRUCTOR);
/* 191 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtEcdsaSignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */