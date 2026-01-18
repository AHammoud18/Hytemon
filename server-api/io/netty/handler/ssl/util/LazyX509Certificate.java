/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Principal;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateExpiredException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.CertificateNotYetValidException;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
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
/*     */ public final class LazyX509Certificate
/*     */   extends X509Certificate
/*     */ {
/*     */   static final CertificateFactory X509_CERT_FACTORY;
/*     */   private final byte[] bytes;
/*     */   private X509Certificate wrapped;
/*     */   
/*     */   static {
/*     */     try {
/*  47 */       X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
/*  48 */     } catch (CertificateException e) {
/*  49 */       throw new ExceptionInInitializerError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazyX509Certificate(byte[] bytes) {
/*  60 */     this.bytes = (byte[])ObjectUtil.checkNotNull(bytes, "bytes");
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
/*  65 */     unwrap().checkValidity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
/*  70 */     unwrap().checkValidity(date);
/*     */   }
/*     */ 
/*     */   
/*     */   public X500Principal getIssuerX500Principal() {
/*  75 */     return unwrap().getIssuerX500Principal();
/*     */   }
/*     */ 
/*     */   
/*     */   public X500Principal getSubjectX500Principal() {
/*  80 */     return unwrap().getSubjectX500Principal();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getExtendedKeyUsage() throws CertificateParsingException {
/*  85 */     return unwrap().getExtendedKeyUsage();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
/*  90 */     return unwrap().getSubjectAlternativeNames();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
/*  95 */     return unwrap().getIssuerAlternativeNames();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey key, Provider sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/* 101 */     unwrap().verify(key, sigProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 106 */     return unwrap().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() {
/* 111 */     return unwrap().getSerialNumber();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getIssuerDN() {
/* 116 */     return unwrap().getIssuerDN();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getSubjectDN() {
/* 121 */     return unwrap().getSubjectDN();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotBefore() {
/* 126 */     return unwrap().getNotBefore();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getNotAfter() {
/* 131 */     return unwrap().getNotAfter();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getTBSCertificate() throws CertificateEncodingException {
/* 136 */     return unwrap().getTBSCertificate();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getSignature() {
/* 141 */     return unwrap().getSignature();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigAlgName() {
/* 146 */     return unwrap().getSigAlgName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigAlgOID() {
/* 151 */     return unwrap().getSigAlgOID();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getSigAlgParams() {
/* 156 */     return unwrap().getSigAlgParams();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getIssuerUniqueID() {
/* 161 */     return unwrap().getIssuerUniqueID();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getSubjectUniqueID() {
/* 166 */     return unwrap().getSubjectUniqueID();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getKeyUsage() {
/* 171 */     return unwrap().getKeyUsage();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBasicConstraints() {
/* 176 */     return unwrap().getBasicConstraints();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/* 181 */     return (byte[])this.bytes.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/* 188 */     unwrap().verify(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
/* 195 */     unwrap().verify(key, sigProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 200 */     return unwrap().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() {
/* 205 */     return unwrap().getPublicKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUnsupportedCriticalExtension() {
/* 210 */     return unwrap().hasUnsupportedCriticalExtension();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getCriticalExtensionOIDs() {
/* 215 */     return unwrap().getCriticalExtensionOIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getNonCriticalExtensionOIDs() {
/* 220 */     return unwrap().getNonCriticalExtensionOIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getExtensionValue(String oid) {
/* 225 */     return unwrap().getExtensionValue(oid);
/*     */   }
/*     */   
/*     */   private X509Certificate unwrap() {
/* 229 */     X509Certificate wrapped = this.wrapped;
/* 230 */     if (wrapped == null) {
/*     */       try {
/* 232 */         wrapped = this.wrapped = (X509Certificate)X509_CERT_FACTORY.generateCertificate(new ByteArrayInputStream(this.bytes));
/*     */       }
/* 234 */       catch (CertificateException e) {
/* 235 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/* 238 */     return wrapped;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\LazyX509Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */