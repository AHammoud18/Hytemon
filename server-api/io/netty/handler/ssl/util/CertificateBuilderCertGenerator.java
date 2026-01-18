/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import io.netty.pkitesting.CertificateBuilder;
/*    */ import io.netty.pkitesting.X509Bundle;
/*    */ import java.security.SecureRandom;
/*    */ import java.time.Instant;
/*    */ import java.util.Date;
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
/*    */ final class CertificateBuilderCertGenerator
/*    */ {
/*    */   static boolean isAvailable() {
/*    */     try {
/* 33 */       new CertificateBuilder();
/* 34 */       return true;
/* 35 */     } catch (Throwable ignore) {
/* 36 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   static void generate(SelfSignedCertificate.Builder config) throws Exception {
/* 41 */     String fqdn = config.fqdn;
/* 42 */     Date notBefore = config.notBefore;
/* 43 */     Date notAfter = config.notAfter;
/* 44 */     String algorithm = config.algorithm;
/* 45 */     SecureRandom random = config.random;
/* 46 */     int bits = config.bits;
/* 47 */     CertificateBuilder builder = new CertificateBuilder();
/* 48 */     builder.setIsCertificateAuthority(true);
/* 49 */     if (fqdn.contains("=")) {
/* 50 */       builder.subject(fqdn);
/*    */     } else {
/* 52 */       builder.subject("CN=" + fqdn);
/*    */     } 
/* 54 */     builder.notBefore(Instant.ofEpochMilli(notBefore.getTime()));
/* 55 */     builder.notAfter(Instant.ofEpochMilli(notAfter.getTime()));
/* 56 */     if (random != null) {
/* 57 */       builder.secureRandom(random);
/*    */     }
/* 59 */     if ("RSA".equals(algorithm)) {
/*    */       CertificateBuilder.Algorithm alg;
/* 61 */       switch (bits) { case 2048:
/* 62 */           alg = CertificateBuilder.Algorithm.rsa2048; break;
/* 63 */         case 3072: alg = CertificateBuilder.Algorithm.rsa3072; break;
/* 64 */         case 4096: alg = CertificateBuilder.Algorithm.rsa4096; break;
/* 65 */         case 8192: alg = CertificateBuilder.Algorithm.rsa8192; break;
/*    */         default:
/* 67 */           throw new IllegalArgumentException("Unsupported RSA bit-width: " + bits); }
/*    */       
/* 69 */       builder.algorithm(alg);
/* 70 */     } else if ("EC".equals(algorithm)) {
/* 71 */       if (bits == 256) {
/* 72 */         builder.algorithm(CertificateBuilder.Algorithm.ecp256);
/* 73 */       } else if (bits == 384) {
/* 74 */         builder.algorithm(CertificateBuilder.Algorithm.ecp384);
/*    */       } else {
/* 76 */         throw new IllegalArgumentException("Unsupported EC-P bit-width: " + bits);
/*    */       } 
/*    */     } 
/* 79 */     X509Bundle bundle = builder.buildSelfSigned();
/* 80 */     config.paths = SelfSignedCertificate.newSelfSignedCertificate(fqdn, bundle.getKeyPair().getPrivate(), bundle.getCertificate());
/* 81 */     config.keypair = bundle.getKeyPair();
/* 82 */     config.privateKey = bundle.getKeyPair().getPrivate();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\CertificateBuilderCertGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */