/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.security.KeyPair;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.SecureRandom;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.Date;
/*    */ import org.bouncycastle.asn1.x500.X500Name;
/*    */ import org.bouncycastle.cert.X509CertificateHolder;
/*    */ import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
/*    */ import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
/*    */ import org.bouncycastle.operator.ContentSigner;
/*    */ import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
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
/*    */ final class BouncyCastleSelfSignedCertGenerator
/*    */ {
/*    */   static String[] generate(String fqdn, KeyPair keypair, SecureRandom random, Date notBefore, Date notAfter, String algorithm) throws Exception {
/* 42 */     PrivateKey key = keypair.getPrivate();
/*    */ 
/*    */     
/* 45 */     X500Name owner = new X500Name("CN=" + fqdn);
/*    */     
/* 47 */     JcaX509v3CertificateBuilder jcaX509v3CertificateBuilder = new JcaX509v3CertificateBuilder(owner, new BigInteger(64, random), notBefore, notAfter, owner, keypair.getPublic());
/*    */ 
/*    */     
/* 50 */     ContentSigner signer = (new JcaContentSignerBuilder(algorithm.equalsIgnoreCase("EC") ? "SHA256withECDSA" : "SHA256WithRSAEncryption")).build(key);
/* 51 */     X509CertificateHolder certHolder = jcaX509v3CertificateBuilder.build(signer);
/*    */ 
/*    */     
/* 54 */     X509Certificate cert = (new JcaX509CertificateConverter()).setProvider(BouncyCastleUtil.getBcProviderJce()).getCertificate(certHolder);
/* 55 */     cert.verify(keypair.getPublic());
/*    */     
/* 57 */     return SelfSignedCertificate.newSelfSignedCertificate(fqdn, key, cert);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\BouncyCastleSelfSignedCertGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */