/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Principal;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ import java.util.Date;
/*     */ import javax.security.cert.CertificateException;
/*     */ import javax.security.cert.CertificateExpiredException;
/*     */ import javax.security.cert.CertificateNotYetValidException;
/*     */ import javax.security.cert.X509Certificate;
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
/*     */ public final class LazyJavaxX509Certificate
/*     */   extends X509Certificate
/*     */ {
/*     */   private final byte[] bytes;
/*     */   private X509Certificate wrapped;
/*     */   
/*     */   public LazyJavaxX509Certificate(byte[] bytes) {
/*  41 */     this.bytes = (byte[])ObjectUtil.checkNotNull(bytes, "bytes");
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
/*  46 */     unwrap().checkValidity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
/*  51 */     unwrap().checkValidity(date);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/*  56 */     return unwrap().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() {
/*  61 */     return unwrap().getSerialNumber();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getIssuerDN() {
/*  66 */     return unwrap().getIssuerDN();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getSubjectDN() {
/*  71 */     return unwrap().getSubjectDN();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotBefore() {
/*  76 */     return unwrap().getNotBefore();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotAfter() {
/*  81 */     return unwrap().getNotAfter();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigAlgName() {
/*  86 */     return unwrap().getSigAlgName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigAlgOID() {
/*  91 */     return unwrap().getSigAlgOID();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getSigAlgParams() {
/*  96 */     return unwrap().getSigAlgParams();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/* 101 */     return (byte[])this.bytes.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getBytes() {
/* 109 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/* 116 */     unwrap().verify(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/* 123 */     unwrap().verify(key, sigProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     return unwrap().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() {
/* 133 */     return unwrap().getPublicKey();
/*     */   }
/*     */   
/*     */   private X509Certificate unwrap() {
/* 137 */     X509Certificate wrapped = this.wrapped;
/* 138 */     if (wrapped == null) {
/*     */       try {
/* 140 */         wrapped = this.wrapped = X509Certificate.getInstance(this.bytes);
/* 141 */       } catch (CertificateException e) {
/* 142 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/* 145 */     return wrapped;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\LazyJavaxX509Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */