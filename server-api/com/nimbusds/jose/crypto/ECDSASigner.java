/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.ActionRequiredForJWSCompletionException;
/*     */ import com.nimbusds.jose.CompletableJWSObjectSigning;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSSigner;
/*     */ import com.nimbusds.jose.JWSSignerOption;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.ECDSA;
/*     */ import com.nimbusds.jose.crypto.impl.ECDSAProvider;
/*     */ import com.nimbusds.jose.crypto.opts.UserAuthenticationRequired;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.ECKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class ECDSASigner
/*     */   extends ECDSAProvider
/*     */   implements JWSSigner
/*     */ {
/*     */   private final PrivateKey privateKey;
/*     */   private final Set<JWSSignerOption> opts;
/*     */   
/*     */   public ECDSASigner(ECPrivateKey privateKey) throws JOSEException {
/* 106 */     this(privateKey, Collections.emptySet());
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
/*     */   public ECDSASigner(ECPrivateKey privateKey, Set<JWSSignerOption> opts) throws JOSEException {
/* 123 */     super(ECDSA.resolveAlgorithm(privateKey));
/*     */     
/* 125 */     this.privateKey = privateKey;
/* 126 */     this.opts = (opts != null) ? opts : Collections.<JWSSignerOption>emptySet();
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
/*     */   public ECDSASigner(PrivateKey privateKey, Curve curve) throws JOSEException {
/* 145 */     this(privateKey, curve, Collections.emptySet());
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
/*     */ 
/*     */   
/*     */   public ECDSASigner(PrivateKey privateKey, Curve curve, Set<JWSSignerOption> opts) throws JOSEException {
/* 167 */     super(ECDSA.resolveAlgorithm(curve));
/*     */     
/* 169 */     if (!"EC".equalsIgnoreCase(privateKey.getAlgorithm())) {
/* 170 */       throw new IllegalArgumentException("The private key algorithm must be EC");
/*     */     }
/*     */     
/* 173 */     this.privateKey = privateKey;
/* 174 */     this.opts = (opts != null) ? opts : Collections.<JWSSignerOption>emptySet();
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
/*     */   public ECDSASigner(ECKey ecJWK) throws JOSEException {
/* 191 */     this(ecJWK, (Set<JWSSignerOption>)null);
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
/*     */   public ECDSASigner(ECKey ecJWK, Set<JWSSignerOption> opts) throws JOSEException {
/* 211 */     super(ECDSA.resolveAlgorithm(ecJWK.getCurve()));
/*     */     
/* 213 */     if (!ecJWK.isPrivate()) {
/* 214 */       throw new JOSEException("The EC JWK doesn't contain a private part");
/*     */     }
/*     */     
/* 217 */     this.privateKey = ecJWK.toPrivateKey();
/* 218 */     this.opts = (opts != null) ? opts : Collections.<JWSSignerOption>emptySet();
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
/*     */   public PrivateKey getPrivateKey() {
/* 232 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL sign(final JWSHeader header, final byte[] signingInput) throws JOSEException {
/*     */     byte[] jcaSignature;
/* 240 */     JWSAlgorithm alg = header.getAlgorithm();
/*     */     
/* 242 */     if (!supportedJWSAlgorithms().contains(alg)) {
/* 243 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, supportedJWSAlgorithms()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 249 */       final Signature dsa = ECDSA.getSignerAndVerifier(alg, getJCAContext().getProvider());
/* 250 */       dsa.initSign(this.privateKey, getJCAContext().getSecureRandom());
/*     */       
/* 252 */       if (this.opts.contains(UserAuthenticationRequired.getInstance()))
/*     */       {
/* 254 */         throw new ActionRequiredForJWSCompletionException("Authenticate user to complete signing", 
/*     */             
/* 256 */             UserAuthenticationRequired.getInstance(), new CompletableJWSObjectSigning()
/*     */             {
/*     */               public Signature getInitializedSignature()
/*     */               {
/* 260 */                 return dsa;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public Base64URL complete() throws JOSEException {
/*     */                 try {
/* 267 */                   dsa.update(signingInput);
/* 268 */                   byte[] jcaSignature = dsa.sign();
/* 269 */                   int rsByteArrayLength = ECDSA.getSignatureByteArrayLength(header.getAlgorithm());
/* 270 */                   byte[] jwsSignature = ECDSA.transcodeSignatureToConcat(jcaSignature, rsByteArrayLength);
/* 271 */                   return Base64URL.encode(jwsSignature);
/* 272 */                 } catch (SignatureException e) {
/* 273 */                   throw new JOSEException(e.getMessage(), e);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       }
/*     */       
/* 279 */       dsa.update(signingInput);
/* 280 */       jcaSignature = dsa.sign();
/*     */     }
/* 282 */     catch (InvalidKeyException|SignatureException e) {
/*     */       
/* 284 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 287 */     int rsByteArrayLength = ECDSA.getSignatureByteArrayLength(header.getAlgorithm());
/* 288 */     byte[] jwsSignature = ECDSA.transcodeSignatureToConcat(jcaSignature, rsByteArrayLength);
/* 289 */     return Base64URL.encode(jwsSignature);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\ECDSASigner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */