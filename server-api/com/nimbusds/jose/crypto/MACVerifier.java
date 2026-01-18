/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.Header;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSVerifier;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.HMAC;
/*     */ import com.nimbusds.jose.crypto.impl.MACProvider;
/*     */ import com.nimbusds.jose.crypto.utils.ConstantTimeUtils;
/*     */ import com.nimbusds.jose.jwk.OctetSequenceKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
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
/*     */ public class MACVerifier
/*     */   extends MACProvider
/*     */   implements JWSVerifier, CriticalHeaderParamsAware
/*     */ {
/*  69 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
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
/*     */   public MACVerifier(byte[] secret) throws JOSEException {
/*  84 */     super(secret);
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
/*     */   
/*     */   public MACVerifier(String secretString) throws JOSEException {
/* 100 */     this(secretString.getBytes(StandardCharset.UTF_8));
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
/*     */   
/*     */   public MACVerifier(SecretKey secretKey) throws JOSEException {
/* 116 */     this(secretKey, (Set<String>)null);
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
/*     */   
/*     */   public MACVerifier(OctetSequenceKey jwk) throws JOSEException {
/* 132 */     this(jwk.toByteArray());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MACVerifier(byte[] secret, Set<String> defCritHeaders) throws JOSEException {
/* 151 */     super(secret);
/*     */     
/* 153 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MACVerifier(SecretKey secretKey, Set<String> defCritHeaders) throws JOSEException {
/* 173 */     super(secretKey);
/*     */     
/* 175 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MACVerifier(OctetSequenceKey jwk, Set<String> defCritHeaders) throws JOSEException {
/* 195 */     this(jwk.toByteArray(), defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 202 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 209 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(JWSHeader header, byte[] signedContent, Base64URL signature) throws JOSEException {
/* 219 */     ensureSecretLengthSatisfiesAlgorithm(header.getAlgorithm());
/*     */     
/* 221 */     if (!this.critPolicy.headerPasses((Header)header)) {
/* 222 */       return false;
/*     */     }
/*     */     
/* 225 */     String jcaAlg = getJCAAlgorithmName(header.getAlgorithm());
/* 226 */     byte[] expectedHMAC = HMAC.compute(jcaAlg, getSecretKey(), signedContent, getJCAContext().getProvider());
/* 227 */     return ConstantTimeUtils.areEqual(expectedHMAC, signature.decode());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\MACVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */