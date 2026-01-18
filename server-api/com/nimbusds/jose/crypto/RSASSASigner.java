/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.ActionRequiredForJWSCompletionException;
/*     */ import com.nimbusds.jose.CompletableJWSObjectSigning;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSSigner;
/*     */ import com.nimbusds.jose.JWSSignerOption;
/*     */ import com.nimbusds.jose.crypto.impl.RSAKeyUtils;
/*     */ import com.nimbusds.jose.crypto.impl.RSASSA;
/*     */ import com.nimbusds.jose.crypto.impl.RSASSAProvider;
/*     */ import com.nimbusds.jose.crypto.opts.AllowWeakRSAKey;
/*     */ import com.nimbusds.jose.crypto.opts.OptionUtils;
/*     */ import com.nimbusds.jose.crypto.opts.UserAuthenticationRequired;
/*     */ import com.nimbusds.jose.jwk.RSAKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class RSASSASigner
/*     */   extends RSASSAProvider
/*     */   implements JWSSigner
/*     */ {
/*     */   private final PrivateKey privateKey;
/*     */   private final Set<JWSSignerOption> opts;
/*     */   
/*     */   public RSASSASigner(PrivateKey privateKey) {
/* 117 */     this(privateKey, false);
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
/*     */   @Deprecated
/*     */   public RSASSASigner(PrivateKey privateKey, boolean allowWeakKey) {
/* 137 */     this(privateKey, 
/*     */         
/* 139 */         allowWeakKey ? (Set)Collections.singleton(AllowWeakRSAKey.getInstance()) : Collections.<JWSSignerOption>emptySet());
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
/*     */   public RSASSASigner(PrivateKey privateKey, Set<JWSSignerOption> opts) {
/* 159 */     if (privateKey instanceof java.security.interfaces.RSAPrivateKey || "RSA".equalsIgnoreCase(privateKey.getAlgorithm())) {
/*     */       
/* 161 */       this.privateKey = privateKey;
/*     */     } else {
/* 163 */       throw new IllegalArgumentException("The private key algorithm must be RSA");
/*     */     } 
/*     */     
/* 166 */     this.opts = (opts != null) ? opts : Collections.<JWSSignerOption>emptySet();
/* 167 */     OptionUtils.ensureMinRSAPrivateKeySize(privateKey, this.opts);
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
/*     */   public RSASSASigner(RSAKey rsaJWK) throws JOSEException {
/* 185 */     this(rsaJWK, Collections.emptySet());
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
/*     */   @Deprecated
/*     */   public RSASSASigner(RSAKey rsaJWK, boolean allowWeakKey) throws JOSEException {
/* 207 */     this(RSAKeyUtils.toRSAPrivateKey(rsaJWK), allowWeakKey);
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
/*     */   public RSASSASigner(RSAKey rsaJWK, Set<JWSSignerOption> opts) throws JOSEException {
/* 227 */     this(RSAKeyUtils.toRSAPrivateKey(rsaJWK), opts);
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
/* 241 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL sign(JWSHeader header, final byte[] signingInput) throws JOSEException {
/* 249 */     final Signature signer = getInitiatedSignature(header);
/*     */     
/* 251 */     if (this.opts.contains(UserAuthenticationRequired.getInstance()))
/*     */     {
/* 253 */       throw new ActionRequiredForJWSCompletionException("Authenticate user to complete signing", 
/*     */           
/* 255 */           UserAuthenticationRequired.getInstance(), new CompletableJWSObjectSigning()
/*     */           {
/*     */             
/*     */             public Signature getInitializedSignature()
/*     */             {
/* 260 */               return signer;
/*     */             }
/*     */ 
/*     */             
/*     */             public Base64URL complete() throws JOSEException {
/* 265 */               return RSASSASigner.this.sign(signingInput, signer);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 271 */     return sign(signingInput, signer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Signature getInitiatedSignature(JWSHeader header) throws JOSEException {
/* 278 */     Signature signer = RSASSA.getSignerAndVerifier(header.getAlgorithm(), getJCAContext().getProvider());
/*     */     try {
/* 280 */       signer.initSign(this.privateKey);
/* 281 */     } catch (InvalidKeyException e) {
/* 282 */       throw new JOSEException("Invalid private RSA key: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 285 */     return signer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Base64URL sign(byte[] signingInput, Signature signer) throws JOSEException {
/*     */     try {
/* 293 */       signer.update(signingInput);
/* 294 */       return Base64URL.encode(signer.sign());
/* 295 */     } catch (SignatureException e) {
/* 296 */       throw new JOSEException("RSA signature exception: " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\RSASSASigner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */