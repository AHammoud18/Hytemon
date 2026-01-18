/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.Header;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSVerifier;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.RSASSA;
/*     */ import com.nimbusds.jose.crypto.impl.RSASSAProvider;
/*     */ import com.nimbusds.jose.jwk.RSAKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ @ThreadSafe
/*     */ public class RSASSAVerifier
/*     */   extends RSASSAProvider
/*     */   implements JWSVerifier, CriticalHeaderParamsAware
/*     */ {
/*  74 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final RSAPublicKey publicKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSASSAVerifier(RSAPublicKey publicKey) {
/*  90 */     this(publicKey, null);
/*     */   }
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
/*     */   public RSASSAVerifier(RSAKey rsaJWK) throws JOSEException {
/* 104 */     this(rsaJWK.toRSAPublicKey(), null);
/*     */   }
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
/*     */   public RSASSAVerifier(RSAPublicKey publicKey, Set<String> defCritHeaders) {
/* 119 */     this.publicKey = Objects.<RSAPublicKey>requireNonNull(publicKey);
/* 120 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAPublicKey getPublicKey() {
/* 131 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 138 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 145 */     return this.critPolicy.getDeferredCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(JWSHeader header, byte[] signedContent, Base64URL signature) throws JOSEException {
/* 155 */     if (!this.critPolicy.headerPasses((Header)header)) {
/* 156 */       return false;
/*     */     }
/*     */     
/* 159 */     Signature verifier = RSASSA.getSignerAndVerifier(header.getAlgorithm(), getJCAContext().getProvider());
/*     */     
/*     */     try {
/* 162 */       verifier.initVerify(this.publicKey);
/*     */     }
/* 164 */     catch (InvalidKeyException e) {
/* 165 */       throw new JOSEException("Invalid public RSA key: " + e.getMessage(), e);
/*     */     } 
/*     */     
/*     */     try {
/* 169 */       verifier.update(signedContent);
/* 170 */       return verifier.verify(signature.decode());
/*     */     }
/* 172 */     catch (SignatureException e) {
/* 173 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\RSASSAVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */