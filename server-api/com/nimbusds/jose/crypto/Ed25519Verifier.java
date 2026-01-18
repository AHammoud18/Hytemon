/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Ed25519Verify;
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.Header;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSVerifier;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.EdDSAProvider;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ @ThreadSafe
/*     */ public class Ed25519Verifier
/*     */   extends EdDSAProvider
/*     */   implements JWSVerifier, CriticalHeaderParamsAware
/*     */ {
/*  59 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final OctetKeyPair publicKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Ed25519Verify tinkVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ed25519Verifier(OctetKeyPair publicKey) throws JOSEException {
/*  78 */     this(publicKey, null);
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
/*     */   public Ed25519Verifier(OctetKeyPair publicKey, Set<String> defCritHeaders) throws JOSEException {
/*  98 */     if (!Curve.Ed25519.equals(publicKey.getCurve())) {
/*  99 */       throw new JOSEException("Ed25519Verifier only supports OctetKeyPairs with crv=Ed25519");
/*     */     }
/*     */     
/* 102 */     if (publicKey.isPrivate()) {
/* 103 */       throw new JOSEException("Ed25519Verifier requires a public key, use OctetKeyPair.toPublicJWK()");
/*     */     }
/*     */     
/* 106 */     this.publicKey = publicKey;
/* 107 */     this.tinkVerifier = new Ed25519Verify(publicKey.getDecodedX());
/* 108 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPair getPublicKey() {
/* 119 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 126 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 133 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(JWSHeader header, byte[] signedContent, Base64URL signature) throws JOSEException {
/* 144 */     JWSAlgorithm alg = header.getAlgorithm();
/* 145 */     if (!JWSAlgorithm.Ed25519.equals(alg) && !JWSAlgorithm.EdDSA.equals(alg)) {
/* 146 */       throw new JOSEException("Ed25519Verifier requires alg=Ed25519 or alg=EdDSA in JWSHeader");
/*     */     }
/*     */ 
/*     */     
/* 150 */     if (!this.critPolicy.headerPasses((Header)header)) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     byte[] jwsSignature = signature.decode();
/*     */     
/*     */     try {
/* 157 */       this.tinkVerifier.verify(jwsSignature, signedContent);
/* 158 */       return true;
/*     */     }
/* 160 */     catch (GeneralSecurityException e) {
/* 161 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\Ed25519Verifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */