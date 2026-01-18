/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.jwt.internal.JsonUtil;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaAlgorithm;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.Enums;
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
/*     */ class JwtEcdsaVerifyKeyManager
/*     */ {
/*     */   @AccessesPartialKey
/*     */   static EcdsaPublicKey toEcdsaPublicKey(JwtEcdsaPublicKey publicKey) throws GeneralSecurityException {
/*  42 */     return publicKey.getEcdsaPublicKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static JwtPublicKeyVerify createFullPrimitive(final JwtEcdsaPublicKey publicKey) throws GeneralSecurityException {
/*  48 */     EcdsaPublicKey ecdsaPublicKey = toEcdsaPublicKey(publicKey);
/*  49 */     final PublicKeyVerify verifier = EcdsaVerifyJce.create(ecdsaPublicKey);
/*     */     
/*  51 */     return new JwtPublicKeyVerify()
/*     */       {
/*     */         public VerifiedJwt verifyAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException
/*     */         {
/*  55 */           JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/*  56 */           verifier.verify(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/*  57 */           JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/*  58 */           JwtFormat.validateHeader(parsedHeader, publicKey
/*     */               
/*  60 */               .getParameters().getAlgorithm().getStandardName(), publicKey
/*  61 */               .getKid(), publicKey
/*  62 */               .getParameters().allowKidAbsent());
/*  63 */           RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/*  64 */           return validator.validate(token);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   static final PrimitiveConstructor<JwtEcdsaPublicKey, JwtPublicKeyVerify> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtEcdsaVerifyKeyManager::createFullPrimitive, JwtEcdsaPublicKey.class, JwtPublicKeyVerify.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final EllipticCurves.CurveType getCurve(JwtEcdsaAlgorithm algorithm) throws GeneralSecurityException {
/*  79 */     switch (algorithm) {
/*     */       case ES256:
/*  81 */         return EllipticCurves.CurveType.NIST_P256;
/*     */       case ES384:
/*  83 */         return EllipticCurves.CurveType.NIST_P384;
/*     */       case ES512:
/*  85 */         return EllipticCurves.CurveType.NIST_P521;
/*     */     } 
/*  87 */     throw new GeneralSecurityException("unknown algorithm " + algorithm.name());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enums.HashType hashForEcdsaAlgorithm(JwtEcdsaAlgorithm algorithm) throws GeneralSecurityException {
/*  93 */     switch (algorithm) {
/*     */       case ES256:
/*  95 */         return Enums.HashType.SHA256;
/*     */       case ES384:
/*  97 */         return Enums.HashType.SHA384;
/*     */       case ES512:
/*  99 */         return Enums.HashType.SHA512;
/*     */     } 
/* 101 */     throw new GeneralSecurityException("unknown algorithm " + algorithm.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final void validateEcdsaAlgorithm(JwtEcdsaAlgorithm algorithm) throws GeneralSecurityException {
/* 108 */     Object unused = hashForEcdsaAlgorithm(algorithm);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/* 114 */     return "type.googleapis.com/google.crypto.tink.JwtEcdsaPublicKey";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtEcdsaVerifyKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */