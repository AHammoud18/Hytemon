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
/*     */ import com.google.crypto.tink.jwt.internal.JwtRsaSsaPkcs1ProtoSerialization;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.charset.StandardCharsets;
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
/*     */ public final class JwtRsaSsaPkcs1SignKeyManager
/*     */ {
/*  58 */   private static final PrivateKeyManager<Void> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  59 */       getKeyType(), Void.class, JwtRsaSsaPkcs1PrivateKey.parser());
/*     */ 
/*     */   
/*  62 */   private static final KeyManager<Void> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  63 */       JwtRsaSsaPkcs1VerifyKeyManager.getKeyType(), Void.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  66 */       JwtRsaSsaPkcs1PublicKey.parser());
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static RsaSsaPkcs1PrivateKey toRsaSsaPkcs1PrivateKey(JwtRsaSsaPkcs1PrivateKey privateKey) throws GeneralSecurityException {
/*  71 */     return privateKey.getRsaSsaPkcs1PrivateKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JwtPublicKeySign createFullPrimitive(final JwtRsaSsaPkcs1PrivateKey privateKey) throws GeneralSecurityException {
/*  78 */     RsaSsaPkcs1PrivateKey rsaSsaPkcs1PrivateKey = toRsaSsaPkcs1PrivateKey(privateKey);
/*  79 */     final PublicKeySign signer = RsaSsaPkcs1SignJce.create(rsaSsaPkcs1PrivateKey);
/*  80 */     final String algorithm = privateKey.getParameters().getAlgorithm().getStandardName();
/*  81 */     return new JwtPublicKeySign()
/*     */       {
/*     */         public String signAndEncode(RawJwt rawJwt) throws GeneralSecurityException
/*     */         {
/*  85 */           String unsignedCompact = JwtFormat.createUnsignedCompact(algorithm, privateKey.getPublicKey().getKid(), rawJwt);
/*  86 */           return JwtFormat.createSignedCompact(unsignedCompact, signer
/*  87 */               .sign(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final PrimitiveConstructor<JwtRsaSsaPkcs1PrivateKey, JwtPublicKeySign> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtRsaSsaPkcs1SignKeyManager::createFullPrimitive, JwtRsaSsaPkcs1PrivateKey.class, JwtPublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/* 101 */     return "type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtRsaSsaPkcs1PrivateKey createKey(JwtRsaSsaPkcs1Parameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 108 */     KeyPairGenerator keyGen = (KeyPairGenerator)EngineFactory.KEY_PAIR_GENERATOR.getInstance("RSA");
/*     */ 
/*     */ 
/*     */     
/* 112 */     RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(parameters.getModulusSizeBits(), new BigInteger(1, parameters.getPublicExponent().toByteArray()));
/* 113 */     keyGen.initialize(spec);
/* 114 */     KeyPair keyPair = keyGen.generateKeyPair();
/* 115 */     RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
/* 116 */     RSAPrivateCrtKey privKey = (RSAPrivateCrtKey)keyPair.getPrivate();
/*     */ 
/*     */ 
/*     */     
/* 120 */     JwtRsaSsaPkcs1PublicKey.Builder jwtRsaSsaPkcs1PublicKeyBuilder = JwtRsaSsaPkcs1PublicKey.builder().setParameters(parameters).setModulus(pubKey.getModulus());
/* 121 */     if (idRequirement != null) {
/* 122 */       jwtRsaSsaPkcs1PublicKeyBuilder.setIdRequirement(idRequirement);
/*     */     }
/* 124 */     JwtRsaSsaPkcs1PublicKey jwtRsaSsaPkcs1PublicKey = jwtRsaSsaPkcs1PublicKeyBuilder.build();
/*     */ 
/*     */     
/* 127 */     return JwtRsaSsaPkcs1PrivateKey.builder()
/* 128 */       .setPublicKey(jwtRsaSsaPkcs1PublicKey)
/* 129 */       .setPrimes(
/* 130 */         SecretBigInteger.fromBigInteger(privKey.getPrimeP(), InsecureSecretKeyAccess.get()), 
/* 131 */         SecretBigInteger.fromBigInteger(privKey.getPrimeQ(), InsecureSecretKeyAccess.get()))
/* 132 */       .setPrivateExponent(
/* 133 */         SecretBigInteger.fromBigInteger(privKey
/* 134 */           .getPrivateExponent(), InsecureSecretKeyAccess.get()))
/* 135 */       .setPrimeExponents(
/* 136 */         SecretBigInteger.fromBigInteger(privKey
/* 137 */           .getPrimeExponentP(), InsecureSecretKeyAccess.get()), 
/* 138 */         SecretBigInteger.fromBigInteger(privKey
/* 139 */           .getPrimeExponentQ(), InsecureSecretKeyAccess.get()))
/* 140 */       .setCrtCoefficient(
/* 141 */         SecretBigInteger.fromBigInteger(privKey
/* 142 */           .getCrtCoefficient(), InsecureSecretKeyAccess.get()))
/* 143 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 147 */   private static final KeyCreator<JwtRsaSsaPkcs1Parameters> KEY_CREATOR = JwtRsaSsaPkcs1SignKeyManager::createKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 155 */     Map<String, Parameters> result = new HashMap<>();
/* 156 */     result.put("JWT_RS256_2048_F4_RAW", 
/*     */         
/* 158 */         JwtRsaSsaPkcs1Parameters.builder()
/* 159 */         .setModulusSizeBits(2048)
/* 160 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 161 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS256)
/* 162 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED)
/* 163 */         .build());
/* 164 */     result.put("JWT_RS256_2048_F4", 
/*     */         
/* 166 */         JwtRsaSsaPkcs1Parameters.builder()
/* 167 */         .setModulusSizeBits(2048)
/* 168 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 169 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS256)
/* 170 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 171 */         .build());
/* 172 */     result.put("JWT_RS256_3072_F4_RAW", 
/*     */         
/* 174 */         JwtRsaSsaPkcs1Parameters.builder()
/* 175 */         .setModulusSizeBits(3072)
/* 176 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 177 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS256)
/* 178 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED)
/* 179 */         .build());
/* 180 */     result.put("JWT_RS256_3072_F4", 
/*     */         
/* 182 */         JwtRsaSsaPkcs1Parameters.builder()
/* 183 */         .setModulusSizeBits(3072)
/* 184 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 185 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS256)
/* 186 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 187 */         .build());
/* 188 */     result.put("JWT_RS384_3072_F4_RAW", 
/*     */         
/* 190 */         JwtRsaSsaPkcs1Parameters.builder()
/* 191 */         .setModulusSizeBits(3072)
/* 192 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 193 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS384)
/* 194 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED)
/* 195 */         .build());
/* 196 */     result.put("JWT_RS384_3072_F4", 
/*     */         
/* 198 */         JwtRsaSsaPkcs1Parameters.builder()
/* 199 */         .setModulusSizeBits(3072)
/* 200 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 201 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS384)
/* 202 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 203 */         .build());
/* 204 */     result.put("JWT_RS512_4096_F4_RAW", 
/*     */         
/* 206 */         JwtRsaSsaPkcs1Parameters.builder()
/* 207 */         .setModulusSizeBits(4096)
/* 208 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 209 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS512)
/* 210 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED)
/* 211 */         .build());
/* 212 */     result.put("JWT_RS512_4096_F4", 
/*     */         
/* 214 */         JwtRsaSsaPkcs1Parameters.builder()
/* 215 */         .setModulusSizeBits(4096)
/* 216 */         .setPublicExponent(JwtRsaSsaPkcs1Parameters.F4)
/* 217 */         .setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm.RS512)
/* 218 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 219 */         .build());
/* 220 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 223 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 231 */     if (!FIPS.isCompatible()) {
/* 232 */       throw new GeneralSecurityException("Can not use RSA SSA PKCS1 in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 235 */     JwtRsaSsaPkcs1ProtoSerialization.register();
/* 236 */     MutablePrimitiveRegistry.globalInstance()
/* 237 */       .registerPrimitiveConstructor(JwtRsaSsaPkcs1VerifyKeyManager.PRIMITIVE_CONSTRUCTOR);
/* 238 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(PRIMITIVE_CONSTRUCTOR);
/* 239 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 240 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, JwtRsaSsaPkcs1Parameters.class);
/* 241 */     KeyManagerRegistry.globalInstance()
/* 242 */       .registerKeyManagerWithFipsCompatibility((KeyManager)legacyPrivateKeyManager, FIPS, newKeyAllowed);
/* 243 */     KeyManagerRegistry.globalInstance()
/* 244 */       .registerKeyManagerWithFipsCompatibility(legacyPublicKeyManager, FIPS, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPkcs1SignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */