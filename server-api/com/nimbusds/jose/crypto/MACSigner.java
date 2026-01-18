/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSSigner;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.crypto.impl.HMAC;
/*     */ import com.nimbusds.jose.crypto.impl.MACProvider;
/*     */ import com.nimbusds.jose.jwk.OctetSequenceKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.StandardCharset;
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
/*     */ 
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
/*     */ public class MACSigner
/*     */   extends MACProvider
/*     */   implements JWSSigner
/*     */ {
/*     */   public MACSigner(byte[] secret) throws KeyLengthException {
/*  76 */     super(secret);
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
/*     */   public MACSigner(String secretString) throws KeyLengthException {
/*  92 */     this(secretString.getBytes(StandardCharset.UTF_8));
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
/*     */   public MACSigner(SecretKey secretKey) throws KeyLengthException {
/* 108 */     super(secretKey);
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
/*     */   public MACSigner(OctetSequenceKey jwk) throws KeyLengthException {
/* 124 */     this(jwk.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL sign(JWSHeader header, byte[] signingInput) throws JOSEException {
/* 132 */     ensureSecretLengthSatisfiesAlgorithm(header.getAlgorithm());
/*     */     
/* 134 */     String jcaAlg = getJCAAlgorithmName(header.getAlgorithm());
/* 135 */     byte[] hmac = HMAC.compute(jcaAlg, getSecretKey(), signingInput, getJCAContext().getProvider());
/* 136 */     return Base64URL.encode(hmac);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\MACSigner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */