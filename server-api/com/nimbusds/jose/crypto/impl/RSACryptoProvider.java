/*    */ package com.nimbusds.jose.crypto.impl;
/*    */ 
/*    */ import com.nimbusds.jose.EncryptionMethod;
/*    */ import com.nimbusds.jose.JWEAlgorithm;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import javax.crypto.SecretKey;
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
/*    */ public abstract class RSACryptoProvider
/*    */   extends BaseJWEProvider
/*    */ {
/*    */   public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;
/* 73 */   public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;
/*    */ 
/*    */   
/*    */   static {
/* 77 */     Set<JWEAlgorithm> algs = new LinkedHashSet<>();
/* 78 */     algs.add(JWEAlgorithm.RSA1_5);
/* 79 */     algs.add(JWEAlgorithm.RSA_OAEP);
/* 80 */     algs.add(JWEAlgorithm.RSA_OAEP_256);
/* 81 */     algs.add(JWEAlgorithm.RSA_OAEP_384);
/* 82 */     algs.add(JWEAlgorithm.RSA_OAEP_512);
/* 83 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
/*    */   }
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
/*    */   protected RSACryptoProvider(SecretKey cek) {
/* 97 */     super(SUPPORTED_ALGORITHMS, ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS, cek);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\RSACryptoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */