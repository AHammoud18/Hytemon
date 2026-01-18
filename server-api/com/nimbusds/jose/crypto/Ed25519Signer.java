/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Ed25519Sign;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSSigner;
/*     */ import com.nimbusds.jose.crypto.impl.EdDSAProvider;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class Ed25519Signer
/*     */   extends EdDSAProvider
/*     */   implements JWSSigner
/*     */ {
/*     */   private final OctetKeyPair privateKey;
/*     */   private final Ed25519Sign tinkSigner;
/*     */   
/*     */   public Ed25519Signer(OctetKeyPair privateKey) throws JOSEException {
/*  80 */     if (!Curve.Ed25519.equals(privateKey.getCurve())) {
/*  81 */       throw new JOSEException("Ed25519Signer only supports OctetKeyPairs with crv=Ed25519");
/*     */     }
/*     */     
/*  84 */     if (!privateKey.isPrivate()) {
/*  85 */       throw new JOSEException("The OctetKeyPair doesn't contain a private part");
/*     */     }
/*     */     
/*  88 */     this.privateKey = privateKey;
/*     */     
/*     */     try {
/*  91 */       this.tinkSigner = new Ed25519Sign(privateKey.getDecodedD());
/*     */     }
/*  93 */     catch (GeneralSecurityException e) {
/*     */       
/*  95 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPair getPrivateKey() {
/* 107 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL sign(JWSHeader header, byte[] signingInput) throws JOSEException {
/*     */     byte[] jwsSignature;
/* 116 */     JWSAlgorithm alg = header.getAlgorithm();
/* 117 */     if (!JWSAlgorithm.Ed25519.equals(alg) && !JWSAlgorithm.EdDSA.equals(alg)) {
/* 118 */       throw new JOSEException("Ed25519Verifier requires alg=Ed25519 or alg=EdDSA in JWSHeader");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 124 */       jwsSignature = this.tinkSigner.sign(signingInput);
/*     */     }
/* 126 */     catch (GeneralSecurityException e) {
/*     */       
/* 128 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 131 */     return Base64URL.encode(jwsSignature);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\Ed25519Signer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */