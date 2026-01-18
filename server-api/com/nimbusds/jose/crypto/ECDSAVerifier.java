/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.Header;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSVerifier;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.ECDSA;
/*     */ import com.nimbusds.jose.crypto.impl.ECDSAProvider;
/*     */ import com.nimbusds.jose.crypto.utils.ECChecks;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.ECKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.interfaces.ECPublicKey;
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
/*     */ @ThreadSafe
/*     */ public class ECDSAVerifier
/*     */   extends ECDSAProvider
/*     */   implements JWSVerifier, CriticalHeaderParamsAware
/*     */ {
/*  71 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ECPublicKey publicKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECDSAVerifier(ECPublicKey publicKey) throws JOSEException {
/*  91 */     this(publicKey, (Set<String>)null);
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
/*     */   public ECDSAVerifier(ECKey ecJWK) throws JOSEException {
/* 106 */     this(ecJWK.toECPublicKey());
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
/*     */   public ECDSAVerifier(ECPublicKey publicKey, Set<String> defCritHeaders) throws JOSEException {
/* 124 */     super(ECDSA.resolveAlgorithm(publicKey));
/*     */     
/* 126 */     this.publicKey = publicKey;
/*     */     
/* 128 */     if (!ECChecks.isPointOnCurve(publicKey, (
/*     */         
/* 130 */         (Curve)Curve.forJWSAlgorithm(supportedECDSAAlgorithm()).iterator().next()).toECParameterSpec())) {
/* 131 */       throw new JOSEException("Curve / public key parameters mismatch");
/*     */     }
/*     */     
/* 134 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECPublicKey getPublicKey() {
/* 145 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 152 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 159 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(JWSHeader header, byte[] signedContent, Base64URL signature) throws JOSEException {
/*     */     byte[] derSignature;
/* 169 */     JWSAlgorithm alg = header.getAlgorithm();
/*     */     
/* 171 */     if (!supportedJWSAlgorithms().contains(alg)) {
/* 172 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, supportedJWSAlgorithms()));
/*     */     }
/*     */     
/* 175 */     if (!this.critPolicy.headerPasses((Header)header)) {
/* 176 */       return false;
/*     */     }
/*     */     
/* 179 */     byte[] jwsSignature = signature.decode();
/*     */ 
/*     */     
/*     */     try {
/* 183 */       ECDSA.ensureLegalSignature(jwsSignature, alg);
/* 184 */     } catch (JOSEException e) {
/* 185 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 190 */       derSignature = ECDSA.transcodeSignatureToDER(jwsSignature);
/* 191 */     } catch (JOSEException e) {
/*     */       
/* 193 */       return false;
/*     */     } 
/*     */     
/* 196 */     Signature sig = ECDSA.getSignerAndVerifier(alg, getJCAContext().getProvider());
/*     */     
/*     */     try {
/* 199 */       sig.initVerify(this.publicKey);
/* 200 */       sig.update(signedContent);
/* 201 */       return sig.verify(derSignature);
/*     */     }
/* 203 */     catch (InvalidKeyException e) {
/* 204 */       throw new JOSEException("Invalid EC public key: " + e.getMessage(), e);
/* 205 */     } catch (SignatureException e) {
/* 206 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\ECDSAVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */