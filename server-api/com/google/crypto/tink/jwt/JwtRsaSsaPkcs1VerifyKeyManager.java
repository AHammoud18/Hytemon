/*    */ package com.google.crypto.tink.jwt;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.PublicKeyVerify;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.jwt.internal.JsonUtil;
/*    */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*    */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.security.GeneralSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class JwtRsaSsaPkcs1VerifyKeyManager
/*    */ {
/*    */   @AccessesPartialKey
/*    */   static RsaSsaPkcs1PublicKey toRsaSsaPkcs1PublicKey(JwtRsaSsaPkcs1PublicKey publicKey) {
/* 37 */     return publicKey.getRsaSsaPkcs1PublicKey();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static JwtPublicKeyVerify createFullPrimitive(final JwtRsaSsaPkcs1PublicKey publicKey) throws GeneralSecurityException {
/* 44 */     RsaSsaPkcs1PublicKey rsaSsaPkcs1PublicKey = toRsaSsaPkcs1PublicKey(publicKey);
/* 45 */     final PublicKeyVerify verifier = RsaSsaPkcs1VerifyJce.create(rsaSsaPkcs1PublicKey);
/*    */     
/* 47 */     return new JwtPublicKeyVerify()
/*    */       {
/*    */         public VerifiedJwt verifyAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException
/*    */         {
/* 51 */           JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/* 52 */           verifier.verify(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/* 53 */           JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/* 54 */           JwtFormat.validateHeader(parsedHeader, publicKey
/*    */               
/* 56 */               .getParameters().getAlgorithm().getStandardName(), publicKey
/* 57 */               .getKid(), publicKey
/* 58 */               .getParameters().allowKidAbsent());
/* 59 */           RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/* 60 */           return validator.validate(token);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   static final PrimitiveConstructor<JwtRsaSsaPkcs1PublicKey, JwtPublicKeyVerify> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtRsaSsaPkcs1VerifyKeyManager::createFullPrimitive, JwtRsaSsaPkcs1PublicKey.class, JwtPublicKeyVerify.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String getKeyType() {
/* 74 */     return "type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PublicKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPkcs1VerifyKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */