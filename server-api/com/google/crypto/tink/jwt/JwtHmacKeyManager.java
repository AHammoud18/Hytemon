/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Mac;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.jwt.internal.JsonUtil;
/*     */ import com.google.crypto.tink.mac.HmacKey;
/*     */ import com.google.crypto.tink.mac.HmacParameters;
/*     */ import com.google.crypto.tink.proto.JwtHmacKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.PrfMac;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public final class JwtHmacKeyManager
/*     */ {
/*     */   @Immutable
/*     */   private static final class JwtHmac
/*     */     implements JwtMac
/*     */   {
/*     */     private final Mac mac;
/*     */     private final String algorithm;
/*     */     private final JwtHmacKey jwtHmacKey;
/*     */     
/*     */     private JwtHmac(Mac plainMac, JwtHmacKey jwtHmacKey) {
/*  63 */       this.algorithm = jwtHmacKey.getParameters().getAlgorithm().getStandardName();
/*  64 */       this.mac = plainMac;
/*  65 */       this.jwtHmacKey = jwtHmacKey;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String computeMacAndEncode(RawJwt rawJwt) throws GeneralSecurityException {
/*  71 */       String unsignedCompact = JwtFormat.createUnsignedCompact(this.algorithm, this.jwtHmacKey.getKid(), rawJwt);
/*  72 */       return JwtFormat.createSignedCompact(unsignedCompact, this.mac
/*  73 */           .computeMac(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public VerifiedJwt verifyMacAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException {
/*  79 */       JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/*  80 */       this.mac.verifyMac(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/*  81 */       JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/*  82 */       JwtFormat.validateHeader(parsedHeader, this.jwtHmacKey
/*     */           
/*  84 */           .getParameters().getAlgorithm().getStandardName(), this.jwtHmacKey
/*  85 */           .getKid(), this.jwtHmacKey
/*  86 */           .getParameters().allowKidAbsent());
/*  87 */       RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/*  88 */       return validator.validate(token);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void validate(JwtHmacParameters parameters) throws GeneralSecurityException {
/*  93 */     int minKeySize = Integer.MAX_VALUE;
/*  94 */     if (parameters.getAlgorithm().equals(JwtHmacParameters.Algorithm.HS256)) {
/*  95 */       minKeySize = 32;
/*     */     }
/*  97 */     if (parameters.getAlgorithm().equals(JwtHmacParameters.Algorithm.HS384)) {
/*  98 */       minKeySize = 48;
/*     */     }
/* 100 */     if (parameters.getAlgorithm().equals(JwtHmacParameters.Algorithm.HS512)) {
/* 101 */       minKeySize = 64;
/*     */     }
/* 103 */     if (parameters.getKeySizeBytes() < minKeySize) {
/* 104 */       throw new GeneralSecurityException("Key size must be at least " + minKeySize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getTagLength(JwtHmacParameters.Algorithm algorithm) throws GeneralSecurityException {
/* 110 */     if (algorithm.equals(JwtHmacParameters.Algorithm.HS256)) {
/* 111 */       return 32;
/*     */     }
/* 113 */     if (algorithm.equals(JwtHmacParameters.Algorithm.HS384)) {
/* 114 */       return 48;
/*     */     }
/* 116 */     if (algorithm.equals(JwtHmacParameters.Algorithm.HS512)) {
/* 117 */       return 64;
/*     */     }
/* 119 */     throw new GeneralSecurityException("Unsupported algorithm: " + algorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   private static HmacParameters.HashType getHmacHashType(JwtHmacParameters.Algorithm algorithm) throws GeneralSecurityException {
/* 124 */     if (algorithm.equals(JwtHmacParameters.Algorithm.HS256)) {
/* 125 */       return HmacParameters.HashType.SHA256;
/*     */     }
/* 127 */     if (algorithm.equals(JwtHmacParameters.Algorithm.HS384)) {
/* 128 */       return HmacParameters.HashType.SHA384;
/*     */     }
/* 130 */     if (algorithm.equals(JwtHmacParameters.Algorithm.HS512)) {
/* 131 */       return HmacParameters.HashType.SHA512;
/*     */     }
/* 133 */     throw new GeneralSecurityException("Unsupported algorithm: " + algorithm);
/*     */   }
/*     */ 
/*     */   
/* 137 */   private static final KeyManager<Void> legacyKeyManager = LegacyKeyManagerImpl.create("type.googleapis.com/google.crypto.tink.JwtHmacKey", Void.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */ 
/*     */       
/* 141 */       JwtHmacKey.parser());
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtMac createFullJwtHmac(JwtHmacKey key) throws GeneralSecurityException {
/* 145 */     validate(key.getParameters());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     HmacKey hmacKey = HmacKey.builder().setParameters(HmacParameters.builder().setKeySizeBytes(key.getParameters().getKeySizeBytes()).setHashType(getHmacHashType(key.getParameters().getAlgorithm())).setTagSizeBytes(getTagLength(key.getParameters().getAlgorithm())).build()).setKeyBytes(key.getKeyBytes()).build();
/* 156 */     return new JwtHmac(PrfMac.create(hmacKey), key);
/*     */   }
/*     */ 
/*     */   
/* 160 */   private static final PrimitiveConstructor<JwtHmacKey, JwtMac> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtHmacKeyManager::createFullJwtHmac, JwtHmacKey.class, JwtMac.class);
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/* 164 */     return "type.googleapis.com/google.crypto.tink.JwtHmacKey";
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtHmacKey createKey(JwtHmacParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 170 */     validate(parameters);
/*     */ 
/*     */ 
/*     */     
/* 174 */     JwtHmacKey.Builder builder = JwtHmacKey.builder().setParameters(parameters).setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()));
/* 175 */     if (parameters.hasIdRequirement()) {
/* 176 */       if (idRequirement == null) {
/* 177 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */       
/* 180 */       builder.setIdRequirement(idRequirement.intValue());
/*     */     } 
/* 182 */     return builder.build();
/*     */   }
/*     */   
/* 185 */   private static final KeyCreator<JwtHmacParameters> KEY_CREATOR = JwtHmacKeyManager::createKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 193 */     Map<String, Parameters> result = new HashMap<>();
/* 194 */     result.put("JWT_HS256_RAW", 
/*     */         
/* 196 */         JwtHmacParameters.builder()
/* 197 */         .setKeySizeBytes(32)
/* 198 */         .setAlgorithm(JwtHmacParameters.Algorithm.HS256)
/* 199 */         .setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED)
/* 200 */         .build());
/* 201 */     result.put("JWT_HS256", 
/*     */         
/* 203 */         JwtHmacParameters.builder()
/* 204 */         .setKeySizeBytes(32)
/* 205 */         .setAlgorithm(JwtHmacParameters.Algorithm.HS256)
/* 206 */         .setKidStrategy(JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 207 */         .build());
/* 208 */     result.put("JWT_HS384_RAW", 
/*     */         
/* 210 */         JwtHmacParameters.builder()
/* 211 */         .setKeySizeBytes(48)
/* 212 */         .setAlgorithm(JwtHmacParameters.Algorithm.HS384)
/* 213 */         .setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED)
/* 214 */         .build());
/* 215 */     result.put("JWT_HS384", 
/*     */         
/* 217 */         JwtHmacParameters.builder()
/* 218 */         .setKeySizeBytes(48)
/* 219 */         .setAlgorithm(JwtHmacParameters.Algorithm.HS384)
/* 220 */         .setKidStrategy(JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 221 */         .build());
/* 222 */     result.put("JWT_HS512_RAW", 
/*     */         
/* 224 */         JwtHmacParameters.builder()
/* 225 */         .setKeySizeBytes(64)
/* 226 */         .setAlgorithm(JwtHmacParameters.Algorithm.HS512)
/* 227 */         .setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED)
/* 228 */         .build());
/* 229 */     result.put("JWT_HS512", 
/*     */         
/* 231 */         JwtHmacParameters.builder()
/* 232 */         .setKeySizeBytes(64)
/* 233 */         .setAlgorithm(JwtHmacParameters.Algorithm.HS512)
/* 234 */         .setKidStrategy(JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID)
/* 235 */         .build());
/* 236 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public TinkFipsUtil.AlgorithmFipsCompatibility fipsStatus() {
/* 240 */     return FIPS;
/*     */   }
/*     */   
/* 243 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 247 */     if (!FIPS.isCompatible()) {
/* 248 */       throw new GeneralSecurityException("Can not use HMAC in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 251 */     JwtHmacProtoSerialization.register();
/* 252 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, JwtHmacParameters.class);
/* 253 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(PRIMITIVE_CONSTRUCTOR);
/* 254 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 255 */     KeyManagerRegistry.globalInstance()
/* 256 */       .registerKeyManagerWithFipsCompatibility(legacyKeyManager, FIPS, newKeyAllowed);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final KeyTemplate hs256Template() {
/* 261 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(JwtHmacParameters.builder().setKeySizeBytes(32).setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED).setAlgorithm(JwtHmacParameters.Algorithm.HS256).build()));
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
/*     */   public static final KeyTemplate hs384Template() {
/* 273 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(JwtHmacParameters.builder().setKeySizeBytes(48).setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED).setAlgorithm(JwtHmacParameters.Algorithm.HS384).build()));
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
/*     */   public static final KeyTemplate hs512Template() {
/* 285 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(JwtHmacParameters.builder().setKeySizeBytes(64).setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED).setAlgorithm(JwtHmacParameters.Algorithm.HS512).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtHmacKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */