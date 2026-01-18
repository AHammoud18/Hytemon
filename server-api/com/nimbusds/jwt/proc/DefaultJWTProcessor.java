/*     */ package com.nimbusds.jwt.proc;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSVerifier;
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.crypto.factories.DefaultJWEDecrypterFactory;
/*     */ import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
/*     */ import com.nimbusds.jose.proc.BadJOSEException;
/*     */ import com.nimbusds.jose.proc.BadJWEException;
/*     */ import com.nimbusds.jose.proc.BadJWSException;
/*     */ import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
/*     */ import com.nimbusds.jose.proc.JOSEObjectTypeVerifier;
/*     */ import com.nimbusds.jose.proc.JWEDecrypterFactory;
/*     */ import com.nimbusds.jose.proc.JWEKeySelector;
/*     */ import com.nimbusds.jose.proc.JWSKeySelector;
/*     */ import com.nimbusds.jose.proc.JWSVerifierFactory;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jwt.EncryptedJWT;
/*     */ import com.nimbusds.jwt.JWT;
/*     */ import com.nimbusds.jwt.JWTClaimsSet;
/*     */ import com.nimbusds.jwt.JWTParser;
/*     */ import com.nimbusds.jwt.PlainJWT;
/*     */ import com.nimbusds.jwt.SignedJWT;
/*     */ import java.security.Key;
/*     */ import java.text.ParseException;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultJWTProcessor<C extends SecurityContext>
/*     */   implements ConfigurableJWTProcessor<C>
/*     */ {
/*  98 */   private JOSEObjectTypeVerifier<C> jwsTypeVerifier = (JOSEObjectTypeVerifier<C>)DefaultJOSEObjectTypeVerifier.JWT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private JOSEObjectTypeVerifier<C> jweTypeVerifier = (JOSEObjectTypeVerifier<C>)DefaultJOSEObjectTypeVerifier.JWT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JWSKeySelector<C> jwsKeySelector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JWTClaimsSetAwareJWSKeySelector<C> claimsSetAwareJWSKeySelector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JWEKeySelector<C> jweKeySelector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private JWSVerifierFactory jwsVerifierFactory = (JWSVerifierFactory)new DefaultJWSVerifierFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private JWEDecrypterFactory jweDecrypterFactory = (JWEDecrypterFactory)new DefaultJWEDecrypterFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   private JWTClaimsSetVerifier<C> claimsVerifier = new DefaultJWTClaimsVerifier<>(null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JOSEObjectTypeVerifier<C> getJWSTypeVerifier() {
/* 147 */     return this.jwsTypeVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWSTypeVerifier(JOSEObjectTypeVerifier<C> jwsTypeVerifier) {
/* 154 */     this.jwsTypeVerifier = jwsTypeVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSKeySelector<C> getJWSKeySelector() {
/* 161 */     return this.jwsKeySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWSKeySelector(JWSKeySelector<C> jwsKeySelector) {
/* 168 */     this.jwsKeySelector = jwsKeySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSetAwareJWSKeySelector<C> getJWTClaimsSetAwareJWSKeySelector() {
/* 175 */     return this.claimsSetAwareJWSKeySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWTClaimsSetAwareJWSKeySelector(JWTClaimsSetAwareJWSKeySelector<C> jwsKeySelector) {
/* 182 */     this.claimsSetAwareJWSKeySelector = jwsKeySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JOSEObjectTypeVerifier<C> getJWETypeVerifier() {
/* 189 */     return this.jweTypeVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWETypeVerifier(JOSEObjectTypeVerifier<C> jweTypeVerifier) {
/* 196 */     this.jweTypeVerifier = jweTypeVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEKeySelector<C> getJWEKeySelector() {
/* 203 */     return this.jweKeySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWEKeySelector(JWEKeySelector<C> jweKeySelector) {
/* 210 */     this.jweKeySelector = jweKeySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSVerifierFactory getJWSVerifierFactory() {
/* 217 */     return this.jwsVerifierFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWSVerifierFactory(JWSVerifierFactory factory) {
/* 224 */     this.jwsVerifierFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEDecrypterFactory getJWEDecrypterFactory() {
/* 231 */     return this.jweDecrypterFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWEDecrypterFactory(JWEDecrypterFactory factory) {
/* 238 */     this.jweDecrypterFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSetVerifier<C> getJWTClaimsSetVerifier() {
/* 245 */     return this.claimsVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJWTClaimsSetVerifier(JWTClaimsSetVerifier<C> claimsVerifier) {
/* 252 */     this.claimsVerifier = claimsVerifier;
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
/*     */   protected JWTClaimsSet extractJWTClaimsSet(JWT jwt) throws BadJWTException {
/*     */     try {
/* 270 */       return jwt.getJWTClaimsSet();
/* 271 */     } catch (ParseException e) {
/*     */       
/* 273 */       throw new BadJWTException(e.getMessage(), e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JWTClaimsSet verifyJWTClaimsSet(JWTClaimsSet claimsSet, C context) throws BadJWTException {
/* 291 */     if (getJWTClaimsSetVerifier() != null) {
/* 292 */       getJWTClaimsSetVerifier().verify(claimsSet, context);
/*     */     }
/* 294 */     return claimsSet;
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
/*     */   
/*     */   protected List<? extends Key> selectKeys(JWSHeader header, JWTClaimsSet claimsSet, C context) throws KeySourceException, BadJOSEException {
/* 317 */     if (getJWTClaimsSetAwareJWSKeySelector() != null)
/* 318 */       return getJWTClaimsSetAwareJWSKeySelector().selectKeys(header, claimsSet, context); 
/* 319 */     if (getJWSKeySelector() != null) {
/* 320 */       return getJWSKeySelector().selectJWSKeys(header, (SecurityContext)context);
/*     */     }
/* 322 */     throw new BadJOSEException("Signed JWT rejected: No JWS key selector is configured");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet process(String jwtString, C context) throws ParseException, BadJOSEException, JOSEException {
/* 331 */     return process(JWTParser.parse(jwtString), context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet process(JWT jwt, C context) throws BadJOSEException, JOSEException {
/* 339 */     if (jwt instanceof SignedJWT) {
/* 340 */       return process((SignedJWT)jwt, context);
/*     */     }
/*     */     
/* 343 */     if (jwt instanceof EncryptedJWT) {
/* 344 */       return process((EncryptedJWT)jwt, context);
/*     */     }
/*     */     
/* 347 */     if (jwt instanceof PlainJWT) {
/* 348 */       return process((PlainJWT)jwt, context);
/*     */     }
/*     */ 
/*     */     
/* 352 */     throw new JOSEException("Unexpected JWT object type: " + jwt.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet process(PlainJWT plainJWT, C context) throws BadJOSEException, JOSEException {
/* 361 */     if (this.jwsTypeVerifier == null) {
/* 362 */       throw new BadJOSEException("Plain JWT rejected: No JWS header typ (type) verifier is configured");
/*     */     }
/* 364 */     this.jwsTypeVerifier.verify(plainJWT.getHeader().getType(), (SecurityContext)context);
/*     */     
/* 366 */     throw new BadJOSEException("Unsecured (plain) JWTs are rejected, extend class to handle");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet process(SignedJWT signedJWT, C context) throws BadJOSEException, JOSEException {
/* 374 */     if (this.jwsTypeVerifier == null) {
/* 375 */       throw new BadJOSEException("Signed JWT rejected: No JWS header typ (type) verifier is configured");
/*     */     }
/*     */     
/* 378 */     this.jwsTypeVerifier.verify(signedJWT.getHeader().getType(), (SecurityContext)context);
/*     */     
/* 380 */     if (getJWSKeySelector() == null && getJWTClaimsSetAwareJWSKeySelector() == null)
/*     */     {
/* 382 */       throw new BadJOSEException("Signed JWT rejected: No JWS key selector is configured");
/*     */     }
/*     */     
/* 385 */     if (getJWSVerifierFactory() == null) {
/* 386 */       throw new JOSEException("No JWS verifier is configured");
/*     */     }
/*     */     
/* 389 */     JWTClaimsSet claimsSet = extractJWTClaimsSet((JWT)signedJWT);
/*     */     
/* 391 */     List<? extends Key> keyCandidates = selectKeys(signedJWT.getHeader(), claimsSet, context);
/*     */     
/* 393 */     if (keyCandidates == null || keyCandidates.isEmpty()) {
/* 394 */       throw new BadJOSEException("Signed JWT rejected: Another algorithm expected, or no matching key(s) found");
/*     */     }
/*     */     
/* 397 */     ListIterator<? extends Key> it = keyCandidates.listIterator();
/*     */     
/* 399 */     while (it.hasNext()) {
/*     */       
/* 401 */       JWSVerifier verifier = getJWSVerifierFactory().createJWSVerifier(signedJWT.getHeader(), it.next());
/*     */       
/* 403 */       if (verifier == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 407 */       boolean validSignature = signedJWT.verify(verifier);
/*     */       
/* 409 */       if (validSignature) {
/* 410 */         return verifyJWTClaimsSet(claimsSet, context);
/*     */       }
/*     */       
/* 413 */       if (!it.hasNext())
/*     */       {
/* 415 */         throw new BadJWSException("Signed JWT rejected: Invalid signature");
/*     */       }
/*     */     } 
/*     */     
/* 419 */     throw new BadJOSEException("JWS object rejected: No matching verifier(s) found");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet process(EncryptedJWT encryptedJWT, C context) throws BadJOSEException, JOSEException {
/* 427 */     if (this.jweTypeVerifier == null) {
/* 428 */       throw new BadJOSEException("Encrypted JWT rejected: No JWE header typ (type) verifier is configured");
/*     */     }
/*     */     
/* 431 */     this.jweTypeVerifier.verify(encryptedJWT.getHeader().getType(), (SecurityContext)context);
/*     */     
/* 433 */     if (getJWEKeySelector() == null)
/*     */     {
/* 435 */       throw new BadJOSEException("Encrypted JWT rejected: No JWE key selector is configured");
/*     */     }
/*     */     
/* 438 */     if (getJWEDecrypterFactory() == null) {
/* 439 */       throw new JOSEException("No JWE decrypter is configured");
/*     */     }
/*     */     
/* 442 */     List<? extends Key> keyCandidates = getJWEKeySelector().selectJWEKeys(encryptedJWT.getHeader(), (SecurityContext)context);
/*     */     
/* 444 */     if (keyCandidates == null || keyCandidates.isEmpty()) {
/* 445 */       throw new BadJOSEException("Encrypted JWT rejected: Another algorithm expected, or no matching key(s) found");
/*     */     }
/*     */     
/* 448 */     ListIterator<? extends Key> it = keyCandidates.listIterator();
/*     */     
/* 450 */     while (it.hasNext()) {
/*     */       
/* 452 */       JWEDecrypter decrypter = getJWEDecrypterFactory().createJWEDecrypter(encryptedJWT.getHeader(), it.next());
/*     */       
/* 454 */       if (decrypter == null) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 459 */         encryptedJWT.decrypt(decrypter);
/*     */       }
/* 461 */       catch (JOSEException e) {
/*     */         
/* 463 */         if (it.hasNext()) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 469 */         throw new BadJWEException("Encrypted JWT rejected: " + e.getMessage(), e);
/*     */       } 
/*     */       
/* 472 */       if ("JWT".equalsIgnoreCase(encryptedJWT.getHeader().getContentType())) {
/*     */ 
/*     */         
/* 475 */         SignedJWT signedJWTPayload = encryptedJWT.getPayload().toSignedJWT();
/*     */         
/* 477 */         if (signedJWTPayload == null)
/*     */         {
/* 479 */           throw new BadJWTException("The payload is not a nested signed JWT");
/*     */         }
/*     */         
/* 482 */         return process(signedJWTPayload, context);
/*     */       } 
/*     */       
/* 485 */       JWTClaimsSet claimsSet = extractJWTClaimsSet((JWT)encryptedJWT);
/* 486 */       return verifyJWTClaimsSet(claimsSet, context);
/*     */     } 
/*     */     
/* 489 */     throw new BadJOSEException("Encrypted JWT rejected: No matching decrypter(s) found");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jwt\proc\DefaultJWTProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */