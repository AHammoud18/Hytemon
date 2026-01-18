/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.JWEObjectJSON;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.crypto.impl.MultiCryptoProvider;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.KeyType;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class MultiDecrypter
/*     */   extends MultiCryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
/*     */   private final JWK jwk;
/*     */   private final String kid;
/*     */   private final URI x5u;
/*     */   private final Base64URL x5t;
/*     */   private final Base64URL x5t256;
/*     */   private final List<Base64> x5c;
/*     */   private final Base64URL thumbprint;
/* 139 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiDecrypter(JWK jwk) throws JOSEException, KeyLengthException {
/* 155 */     this(jwk, null);
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
/*     */   public MultiDecrypter(JWK jwk, Set<String> defCritHeaders) throws JOSEException, KeyLengthException {
/* 175 */     super(null);
/*     */     
/* 177 */     if (jwk == null) {
/* 178 */       throw new IllegalArgumentException("The private key (JWK) must not be null");
/*     */     }
/* 180 */     this.jwk = jwk;
/* 181 */     this.kid = jwk.getKeyID();
/* 182 */     this.x5c = jwk.getX509CertChain();
/* 183 */     this.x5u = jwk.getX509CertURL();
/* 184 */     this.x5t = jwk.getX509CertThumbprint();
/* 185 */     this.x5t256 = jwk.getX509CertSHA256Thumbprint();
/* 186 */     this.thumbprint = jwk.computeThumbprint();
/*     */     
/* 188 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 195 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 202 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean jwkMatched(JWEHeader recipientHeader) throws JOSEException {
/* 209 */     if (this.thumbprint.toString().equals(recipientHeader.getKeyID())) {
/* 210 */       return true;
/*     */     }
/* 212 */     JWK rjwk = recipientHeader.getJWK();
/* 213 */     if (rjwk != null && this.thumbprint.equals(rjwk.computeThumbprint())) {
/* 214 */       return true;
/*     */     }
/* 216 */     if (this.x5u != null && this.x5u.equals(recipientHeader.getX509CertURL())) {
/* 217 */       return true;
/*     */     }
/* 219 */     if (this.x5t != null && this.x5t.equals(recipientHeader.getX509CertThumbprint())) {
/* 220 */       return true;
/*     */     }
/* 222 */     if (this.x5t256 != null && this.x5t256.equals(recipientHeader.getX509CertSHA256Thumbprint())) {
/* 223 */       return true;
/*     */     }
/* 225 */     List<Base64> rx5c = recipientHeader.getX509CertChain();
/* 226 */     if (this.x5c != null && rx5c != null && this.x5c.containsAll(rx5c) && rx5c.containsAll(this.x5c)) {
/* 227 */       return true;
/*     */     }
/* 229 */     if (this.kid != null && this.kid.equals(recipientHeader.getKeyID())) {
/* 230 */       return true;
/*     */     }
/* 232 */     return false;
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
/*     */ 
/*     */ 
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
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag) throws JOSEException {
/* 267 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
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
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, byte[] aad) throws JOSEException {
/*     */     JWEDecrypter decrypter;
/* 280 */     if (iv == null) {
/* 281 */       throw new JOSEException("Unexpected present JWE initialization vector (IV)");
/*     */     }
/*     */     
/* 284 */     if (authTag == null) {
/* 285 */       throw new JOSEException("Missing JWE authentication tag");
/*     */     }
/*     */     
/* 288 */     if (aad == null) {
/* 289 */       throw new JOSEException("Missing JWE additional authenticated data (AAD)");
/*     */     }
/*     */ 
/*     */     
/* 293 */     KeyType kty = this.jwk.getKeyType();
/* 294 */     Set<String> defCritHeaders = this.critPolicy.getDeferredCriticalHeaderParams();
/* 295 */     JWEObjectJSON.Recipient recipient = null;
/* 296 */     JWEHeader recipientHeader = null;
/*     */ 
/*     */     
/*     */     try {
/* 300 */       for (Object recipientMap : JSONObjectUtils.getJSONArray(JSONObjectUtils.parse(encryptedKey.decodeToString()), "recipients")) {
/*     */         try {
/* 302 */           recipient = JWEObjectJSON.Recipient.parse((Map)recipientMap);
/* 303 */           recipientHeader = (JWEHeader)header.join(recipient.getUnprotectedHeader());
/* 304 */         } catch (Exception e) {
/* 305 */           throw new JOSEException(e.getMessage());
/*     */         } 
/* 307 */         if (jwkMatched(recipientHeader)) {
/*     */           break;
/*     */         }
/* 310 */         recipientHeader = null;
/*     */       } 
/* 312 */     } catch (Exception e) {
/*     */       
/* 314 */       recipientHeader = header;
/* 315 */       recipient = new JWEObjectJSON.Recipient(null, encryptedKey);
/*     */     } 
/*     */     
/* 318 */     if (recipientHeader == null) {
/* 319 */       throw new JOSEException("No recipient found");
/*     */     }
/*     */     
/* 322 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(recipientHeader);
/* 323 */     this.critPolicy.ensureHeaderPasses(recipientHeader);
/*     */     
/* 325 */     if (KeyType.RSA.equals(kty) && RSADecrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 326 */       decrypter = new RSADecrypter(this.jwk.toRSAKey().toRSAPrivateKey(), defCritHeaders);
/* 327 */     } else if (KeyType.EC.equals(kty) && ECDHDecrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 328 */       decrypter = new ECDHDecrypter(this.jwk.toECKey().toECPrivateKey(), defCritHeaders);
/* 329 */     } else if (KeyType.OCT.equals(kty) && AESDecrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 330 */       decrypter = new AESDecrypter(this.jwk.toOctetSequenceKey().toSecretKey("AES"), defCritHeaders);
/* 331 */     } else if (KeyType.OCT.equals(kty) && DirectDecrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 332 */       decrypter = new DirectDecrypter(this.jwk.toOctetSequenceKey().toSecretKey("AES"), defCritHeaders);
/* 333 */     } else if (KeyType.OKP.equals(kty) && X25519Decrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 334 */       decrypter = new X25519Decrypter(this.jwk.toOctetKeyPair(), defCritHeaders);
/*     */     } else {
/* 336 */       throw new JOSEException("Unsupported algorithm");
/*     */     } 
/*     */     
/* 339 */     return decrypter.decrypt(recipientHeader, recipient.getEncryptedKey(), iv, cipherText, authTag, aad);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\MultiDecrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */