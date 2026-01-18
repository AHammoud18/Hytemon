/*    */ package com.google.crypto.tink.jwt;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.PublicKeyVerify;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.jwt.internal.JsonUtil;
/*    */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*    */ import com.google.crypto.tink.subtle.RsaSsaPssVerifyJce;
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
/*    */ final class JwtRsaSsaPssVerifyKeyManager
/*    */ {
/*    */   @AccessesPartialKey
/*    */   static RsaSsaPssPublicKey toRsaSsaPssPublicKey(JwtRsaSsaPssPublicKey publicKey) {
/* 37 */     return publicKey.getRsaSsaPssPublicKey();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static JwtPublicKeyVerify createFullPrimitive(final JwtRsaSsaPssPublicKey publicKey) throws GeneralSecurityException {
/* 43 */     RsaSsaPssPublicKey rsaSsaPssPublicKey = toRsaSsaPssPublicKey(publicKey);
/* 44 */     final PublicKeyVerify verifier = RsaSsaPssVerifyJce.create(rsaSsaPssPublicKey);
/*    */     
/* 46 */     return new JwtPublicKeyVerify()
/*    */       {
/*    */         public VerifiedJwt verifyAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException
/*    */         {
/* 50 */           JwtFormat.Parts parts = JwtFormat.splitSignedCompact(compact);
/* 51 */           verifier.verify(parts.signatureOrMac, parts.unsignedCompact.getBytes(StandardCharsets.US_ASCII));
/* 52 */           JsonObject parsedHeader = JsonUtil.parseJson(parts.header);
/* 53 */           JwtFormat.validateHeader(parsedHeader, publicKey
/*    */               
/* 55 */               .getParameters().getAlgorithm().getStandardName(), publicKey
/* 56 */               .getKid(), publicKey
/* 57 */               .getParameters().allowKidAbsent());
/* 58 */           RawJwt token = RawJwt.fromJsonPayload(JwtFormat.getTypeHeader(parsedHeader), parts.payload);
/* 59 */           return validator.validate(token);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 66 */   static final PrimitiveConstructor<JwtRsaSsaPssPublicKey, JwtPublicKeyVerify> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(JwtRsaSsaPssVerifyKeyManager::createFullPrimitive, JwtRsaSsaPssPublicKey.class, JwtPublicKeyVerify.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String getKeyType() {
/* 72 */     return "type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPublicKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPssVerifyKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */