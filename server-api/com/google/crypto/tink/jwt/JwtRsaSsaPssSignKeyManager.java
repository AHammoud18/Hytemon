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
/*     */ import com.google.crypto.tink.jwt.internal.JwtRsaSsaPssProtoSerialization;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssSignJce;
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
/*     */ public final class JwtRsaSsaPssSignKeyManager
/*     */ {
/*  58 */   private static final PrivateKeyManager<Void> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  59 */       getKeyType(), Void.class, JwtRsaSsaPssPrivateKey.parser());
/*     */ 
/*     */   
/*  62 */   private static final KeyManager<Void> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  63 */       JwtRsaSsaPssVerifyKeyManager.getKeyType(), Void.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  66 */       JwtRsaSsaPssPublicKey.parser());
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtRsaSsaPssPrivateKey createKey(JwtRsaSsaPssParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  72 */     KeyPairGenerator keyGen = (KeyPairGenerator)EngineFactory.KEY_PAIR_GENERATOR.getInstance("RSA");
/*     */ 
/*     */ 
/*     */     
/*  76 */     RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(parameters.getModulusSizeBits(), new BigInteger(1, parameters.getPublicExponent().toByteArray()));
/*  77 */     keyGen.initialize(spec);
/*  78 */     KeyPair keyPair = keyGen.generateKeyPair();
/*  79 */     RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
/*  80 */     RSAPrivateCrtKey privKey = (RSAPrivateCrtKey)keyPair.getPrivate();
/*     */ 
/*     */     
/*  83 */     JwtRsaSsaPssPublicKey.Builder jwtRsaSsaPssPublicKeyBuilder = JwtRsaSsaPssPublicKey.builder().setParameters(parameters).setModulus(pubKey.getModulus());
/*  84 */     if (idRequirement != null) {
/*  85 */       jwtRsaSsaPssPublicKeyBuilder.setIdRequirement(idRequirement);
/*     */     }
/*  87 */     JwtRsaSsaPssPublicKey jwtRsaSsaPssPublicKey = jwtRsaSsaPssPublicKeyBuilder.build();
/*     */     
/*  89 */     return JwtRsaSsaPssPrivateKey.builder()
/*  90 */       .setPublicKey(jwtRsaSsaPssPublicKey)
/*  91 */       .setPrimes(
/*  92 */         SecretBigInteger.fromBigInteger(privKey.getPrimeP(), InsecureSecretKeyAccess.get()), 
/*  93 */         SecretBigInteger.fromBigInteger(privKey.getPrimeQ(), InsecureSecretKeyAccess.get()))
/*  94 */       .setPrivateExponent(
/*  95 */         SecretBigInteger.fromBigInteger(privKey
/*  96 */           .getPrivateExponent(), InsecureSecretKeyAccess.get()))
/*  97 */       .setPrimeExponents(
/*  98 */         SecretBigInteger.fromBigInteger(privKey
/*  99 */           .getPrimeExponentP(), InsecureSecretKeyAccess.get()), 
/* 100 */         SecretBigInteger.fromBigInteger(privKey
/* 101 */           .getPrimeExponentQ(), InsecureSecretKeyAccess.get()))
/* 102 */       .setCrtCoefficient(
/* 103 */         SecretBigInteger.fromBigInteger(privKey
/* 104 */           .getCrtCoefficient(), InsecureSecretKeyAccess.get()))
/* 105 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 109 */   private static final KeyCreator<JwtRsaSsaPssParameters> KEY_CREATOR = JwtRsaSsaPssSignKeyManager::createKey;
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPssPrivateKey toRsaSsaPssPrivateKey(JwtRsaSsaPssPrivateKey privateKey) {
/* 115 */     return privateKey.getRsaSsaPssPrivateKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JwtPublicKeySign createFullPrimitive(final JwtRsaSsaPssPrivateKey privateKey) throws GeneralSecurityException {
/* 122 */     RsaSsaPssPrivateKey rsaSsaPssPrivateKey = toRsaSsaPssPrivateKey(privateKey);
/* 123 */     final PublicKeySign signer = RsaSsaPssSignJce.create(rsaSsaPssPrivateKey);
/* 124 */     final String algorithm = privateKey.getParameters().getAlgorithm().getStandardName();
/* 125 */     return new JwtPublicKeySign()
/*     */       {
/*     */         public String signAndEncode(RawJwt rawJwt) throws GeneralSecurityException
/*     */         {
/* 129 */           String unsignedCompact = JwtFormat.createUnsignedCompact(algorithm, privateKey.getPublicKey().getKid(), rawJwt);
/* 130 */           return JwtFormat.createSignedCompact(unsignedCompact, signer
/* 131 */               .sign(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private static final PrimitiveConstructor<JwtRsaSsaPssPrivateKey, JwtPublicKeySign> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtRsaSsaPssSignKeyManager::createFullPrimitive, JwtRsaSsaPssPrivateKey.class, JwtPublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/* 145 */     return "type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 153 */     Map<String, Parameters> result = new HashMap<>();
/* 154 */     result.put("JWT_PS256_2048_F4_RAW", 
/*     */         
/* 156 */         JwtRsaSsaPssParameters.builder()
/* 157 */         .setModulusSizeBits(2048)
/* 158 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 159 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS256)
/* 160 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.IGNORED)
/* 161 */         .build());
/* 162 */     result.put("JWT_PS256_2048_F4", 
/*     */         
/* 164 */         JwtRsaSsaPssParameters.builder()
/* 165 */         .setModulusSizeBits(2048)
/* 166 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 167 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS256)
/* 168 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 169 */         .build());
/* 170 */     result.put("JWT_PS256_3072_F4_RAW", 
/*     */         
/* 172 */         JwtRsaSsaPssParameters.builder()
/* 173 */         .setModulusSizeBits(3072)
/* 174 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 175 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS256)
/* 176 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.IGNORED)
/* 177 */         .build());
/* 178 */     result.put("JWT_PS256_3072_F4", 
/*     */         
/* 180 */         JwtRsaSsaPssParameters.builder()
/* 181 */         .setModulusSizeBits(3072)
/* 182 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 183 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS256)
/* 184 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 185 */         .build());
/* 186 */     result.put("JWT_PS384_3072_F4_RAW", 
/*     */         
/* 188 */         JwtRsaSsaPssParameters.builder()
/* 189 */         .setModulusSizeBits(3072)
/* 190 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 191 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS384)
/* 192 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.IGNORED)
/* 193 */         .build());
/* 194 */     result.put("JWT_PS384_3072_F4", 
/*     */         
/* 196 */         JwtRsaSsaPssParameters.builder()
/* 197 */         .setModulusSizeBits(3072)
/* 198 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 199 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS384)
/* 200 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 201 */         .build());
/* 202 */     result.put("JWT_PS512_4096_F4_RAW", 
/*     */         
/* 204 */         JwtRsaSsaPssParameters.builder()
/* 205 */         .setModulusSizeBits(4096)
/* 206 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 207 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS512)
/* 208 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.IGNORED)
/* 209 */         .build());
/* 210 */     result.put("JWT_PS512_4096_F4", 
/*     */         
/* 212 */         JwtRsaSsaPssParameters.builder()
/* 213 */         .setModulusSizeBits(4096)
/* 214 */         .setPublicExponent(JwtRsaSsaPssParameters.F4)
/* 215 */         .setAlgorithm(JwtRsaSsaPssParameters.Algorithm.PS512)
/* 216 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 217 */         .build());
/* 218 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 221 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 229 */     if (!FIPS.isCompatible()) {
/* 230 */       throw new GeneralSecurityException("Can not use RSA SSA PSS in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 233 */     JwtRsaSsaPssProtoSerialization.register();
/* 234 */     MutablePrimitiveRegistry.globalInstance()
/* 235 */       .registerPrimitiveConstructor(JwtRsaSsaPssVerifyKeyManager.PRIMITIVE_CONSTRUCTOR);
/* 236 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(PRIMITIVE_CONSTRUCTOR);
/* 237 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 238 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, JwtRsaSsaPssParameters.class);
/* 239 */     KeyManagerRegistry.globalInstance()
/* 240 */       .registerKeyManagerWithFipsCompatibility((KeyManager)legacyPrivateKeyManager, FIPS, newKeyAllowed);
/* 241 */     KeyManagerRegistry.globalInstance()
/* 242 */       .registerKeyManagerWithFipsCompatibility(legacyPublicKeyManager, FIPS, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPssSignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */