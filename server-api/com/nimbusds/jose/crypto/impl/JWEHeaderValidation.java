/*    */ package com.nimbusds.jose.crypto.impl;
/*    */ 
/*    */ import com.nimbusds.jose.JOSEException;
/*    */ import com.nimbusds.jose.JWEAlgorithm;
/*    */ import com.nimbusds.jose.JWEHeader;
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
/*    */ public class JWEHeaderValidation
/*    */ {
/*    */   public static JWEAlgorithm getAlgorithmAndEnsureNotNull(JWEHeader jweHeader) throws JOSEException {
/* 48 */     JWEAlgorithm alg = jweHeader.getAlgorithm();
/* 49 */     if (alg == null) {
/* 50 */       throw new JOSEException("The algorithm \"alg\" header parameter must not be null");
/*    */     }
/* 52 */     return alg;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\JWEHeaderValidation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */