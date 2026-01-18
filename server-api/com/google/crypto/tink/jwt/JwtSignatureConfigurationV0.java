/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Configuration;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.InternalConfiguration;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.jwt.internal.JsonUtil;
/*     */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*     */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssSignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssVerifyJce;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class JwtSignatureConfigurationV0
/*     */ {
/*  57 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*     */   
/*  59 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   private static InternalConfiguration create() {
/*     */     try {
/*  64 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*     */ 
/*     */       
/*  67 */       JwtPublicKeySignWrapper.registerToInternalPrimitiveRegistry(builder);
/*  68 */       builder.registerPrimitiveConstructor(
/*  69 */           PrimitiveConstructor.create(JwtSignatureConfigurationV0::createJwtEcdsaSign, JwtEcdsaPrivateKey.class, JwtPublicKeySign.class));
/*     */ 
/*     */ 
/*     */       
/*  73 */       builder.registerPrimitiveConstructor(
/*  74 */           PrimitiveConstructor.create(JwtSignatureConfigurationV0::createJwtRsaSsaPkcs1Sign, JwtRsaSsaPkcs1PrivateKey.class, JwtPublicKeySign.class));
/*     */ 
/*     */ 
/*     */       
/*  78 */       builder.registerPrimitiveConstructor(
/*  79 */           PrimitiveConstructor.create(JwtSignatureConfigurationV0::createJwtRsaSsaPssSign, JwtRsaSsaPssPrivateKey.class, JwtPublicKeySign.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       JwtPublicKeyVerifyWrapper.registerToInternalPrimitiveRegistry(builder);
/*  86 */       builder.registerPrimitiveConstructor(
/*  87 */           PrimitiveConstructor.create(JwtSignatureConfigurationV0::createJwtEcdsaVerify, JwtEcdsaPublicKey.class, JwtPublicKeyVerify.class));
/*     */ 
/*     */ 
/*     */       
/*  91 */       builder.registerPrimitiveConstructor(
/*  92 */           PrimitiveConstructor.create(JwtSignatureConfigurationV0::createJwtRsaSsaPkcs1Verify, JwtRsaSsaPkcs1PublicKey.class, JwtPublicKeyVerify.class));
/*     */ 
/*     */ 
/*     */       
/*  96 */       builder.registerPrimitiveConstructor(
/*  97 */           PrimitiveConstructor.create(JwtSignatureConfigurationV0::createJwtRsaSsaPssVerify, JwtRsaSsaPssPublicKey.class, JwtPublicKeyVerify.class));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       return InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/* 103 */     } catch (GeneralSecurityException e) {
/* 104 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static EcdsaPublicKey toEcdsaPublicKey(JwtEcdsaPublicKey publicKey) {
/* 110 */     return publicKey.getEcdsaPublicKey();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static EcdsaPrivateKey toEcdsaPrivateKey(JwtEcdsaPrivateKey privateKey) throws GeneralSecurityException {
/* 116 */     return privateKey.getEcdsaPrivateKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtPublicKeySign createJwtEcdsaSign(JwtEcdsaPrivateKey privateKey) throws GeneralSecurityException {
/* 122 */     EcdsaPrivateKey ecdsaPrivateKey = toEcdsaPrivateKey(privateKey);
/* 123 */     PublicKeySign signer = EcdsaSignJce.create(ecdsaPrivateKey);
/* 124 */     String algorithm = privateKey.getParameters().getAlgorithm().getStandardName();
/* 125 */     return rawJwt -> {
/*     */         String unsignedCompact = JwtFormat.createUnsignedCompact(algorithm, privateKey.getPublicKey().getKid(), rawJwt);
/*     */         return JwtFormat.createSignedCompact(unsignedCompact, signer.sign(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPkcs1PrivateKey toRsaSsaPkcs1PrivateKey(JwtRsaSsaPkcs1PrivateKey privateKey) {
/* 136 */     return privateKey.getRsaSsaPkcs1PrivateKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtPublicKeySign createJwtRsaSsaPkcs1Sign(JwtRsaSsaPkcs1PrivateKey privateKey) throws GeneralSecurityException {
/* 142 */     RsaSsaPkcs1PrivateKey rsaSsaPkcs1PrivateKey = toRsaSsaPkcs1PrivateKey(privateKey);
/* 143 */     PublicKeySign signer = RsaSsaPkcs1SignJce.create(rsaSsaPkcs1PrivateKey);
/* 144 */     String algorithm = privateKey.getParameters().getAlgorithm().getStandardName();
/* 145 */     return rawJwt -> {
/*     */         String unsignedCompact = JwtFormat.createUnsignedCompact(algorithm, privateKey.getPublicKey().getKid(), rawJwt);
/*     */         return JwtFormat.createSignedCompact(unsignedCompact, signer.sign(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPssPrivateKey toRsaSsaPssPrivateKey(JwtRsaSsaPssPrivateKey privateKey) {
/* 155 */     return privateKey.getRsaSsaPssPrivateKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtPublicKeySign createJwtRsaSsaPssSign(JwtRsaSsaPssPrivateKey privateKey) throws GeneralSecurityException {
/* 162 */     RsaSsaPssPrivateKey rsaSsaPssPrivateKey = toRsaSsaPssPrivateKey(privateKey);
/* 163 */     PublicKeySign signer = RsaSsaPssSignJce.create(rsaSsaPssPrivateKey);
/* 164 */     String algorithm = privateKey.getParameters().getAlgorithm().getStandardName();
/* 165 */     return rawJwt -> {
/*     */         String unsignedCompact = JwtFormat.createUnsignedCompact(algorithm, privateKey.getPublicKey().getKid(), rawJwt);
/*     */         return JwtFormat.createSignedCompact(unsignedCompact, signer.sign(unsignedCompact.getBytes(StandardCharsets.US_ASCII)));
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtPublicKeyVerify createJwtEcdsaVerify(JwtEcdsaPublicKey publicKey) throws GeneralSecurityException {
/* 176 */     EcdsaPublicKey ecdsaPublicKey = toEcdsaPublicKey(publicKey);
/* 177 */     PublicKeyVerify verifier = EcdsaVerifyJce.create(ecdsaPublicKey);
/*     */     
/* 179 */     return (compact, validator) -> {
/*     */         JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/*     */         verifier.verify(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/*     */         JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/*     */         JwtFormat.validateHeader(parsedHeader, publicKey.getParameters().getAlgorithm().getStandardName(), publicKey.getKid(), publicKey.getParameters().allowKidAbsent());
/*     */         RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/*     */         return validator.validate(token);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPkcs1PublicKey toRsaSsaPkcs1PublicKey(JwtRsaSsaPkcs1PublicKey publicKey) {
/* 195 */     return publicKey.getRsaSsaPkcs1PublicKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtPublicKeyVerify createJwtRsaSsaPkcs1Verify(JwtRsaSsaPkcs1PublicKey publicKey) throws GeneralSecurityException {
/* 201 */     RsaSsaPkcs1PublicKey rsaSsaPkcs1PublicKey = toRsaSsaPkcs1PublicKey(publicKey);
/* 202 */     PublicKeyVerify verifier = RsaSsaPkcs1VerifyJce.create(rsaSsaPkcs1PublicKey);
/*     */     
/* 204 */     return (compact, validator) -> {
/*     */         JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/*     */         verifier.verify(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/*     */         JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/*     */         JwtFormat.validateHeader(parsedHeader, publicKey.getParameters().getAlgorithm().getStandardName(), publicKey.getKid(), publicKey.getParameters().allowKidAbsent());
/*     */         RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/*     */         return validator.validate(token);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPssPublicKey toRsaSsaPssPublicKey(JwtRsaSsaPssPublicKey publicKey) {
/* 220 */     return publicKey.getRsaSsaPssPublicKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtPublicKeyVerify createJwtRsaSsaPssVerify(JwtRsaSsaPssPublicKey publicKey) throws GeneralSecurityException {
/* 226 */     RsaSsaPssPublicKey rsaSsaPssPublicKey = toRsaSsaPssPublicKey(publicKey);
/* 227 */     PublicKeyVerify verifier = RsaSsaPssVerifyJce.create(rsaSsaPssPublicKey);
/*     */     
/* 229 */     return (compact, validator) -> {
/*     */         JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/*     */         verifier.verify(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/*     */         JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/*     */         JwtFormat.validateHeader(parsedHeader, publicKey.getParameters().getAlgorithm().getStandardName(), publicKey.getKid(), publicKey.getParameters().allowKidAbsent());
/*     */         RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/*     */         return validator.validate(token);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Configuration get() throws GeneralSecurityException {
/* 245 */     if (!FIPS.isCompatible()) {
/* 246 */       throw new GeneralSecurityException("Cannot use JwtSignatureConfigurationV0, as BoringCrypto module is needed for FIPS compatibility");
/*     */     }
/*     */ 
/*     */     
/* 250 */     return (Configuration)INTERNAL_CONFIGURATION;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtSignatureConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */