/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.Payload;
/*     */ import com.nimbusds.jose.UnprotectedHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.crypto.impl.MultiCryptoProvider;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.jwk.KeyType;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONArrayUtils;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
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
/*     */ public class MultiEncrypter
/*     */   extends MultiCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*  97 */   private static final String[] RECIPIENT_HEADER_PARAMS = new String[] { "kid", "alg", "x5u", "x5t", "x5t#S256", "x5c" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JWKSet keys;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiEncrypter(JWKSet keys) throws KeyLengthException {
/* 124 */     this(keys, findDirectCEK(keys));
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
/*     */   public MultiEncrypter(JWKSet keys, SecretKey contentEncryptionKey) throws KeyLengthException {
/* 146 */     super(contentEncryptionKey);
/*     */     
/* 148 */     for (JWK jwk : keys.getKeys()) {
/* 149 */       KeyType kty = jwk.getKeyType();
/* 150 */       if (jwk.getAlgorithm() == null) {
/* 151 */         throw new IllegalArgumentException("Each JWK must specify a key encryption algorithm");
/*     */       }
/* 153 */       JWEAlgorithm alg = JWEAlgorithm.parse(jwk.getAlgorithm().toString());
/* 154 */       if (JWEAlgorithm.DIR.equals(alg) && KeyType.OCT
/* 155 */         .equals(kty) && 
/* 156 */         !jwk.toOctetSequenceKey().toSecretKey("AES").equals(contentEncryptionKey)) {
/* 157 */         throw new IllegalArgumentException("Bad CEK");
/*     */       }
/* 159 */       if ((!KeyType.RSA.equals(kty) || !RSAEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) && (
/* 160 */         !KeyType.EC.equals(kty) || !ECDHEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) && (
/* 161 */         !KeyType.OCT.equals(kty) || !AESEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) && (
/* 162 */         !KeyType.OCT.equals(kty) || !DirectEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) && (
/* 163 */         !KeyType.OKP.equals(kty) || !X25519Encrypter.SUPPORTED_ALGORITHMS.contains(alg))) {
/* 164 */         throw new IllegalArgumentException("Unsupported key encryption algorithm: " + alg);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     this.keys = keys;
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
/*     */   private static SecretKey findDirectCEK(JWKSet keys) {
/* 181 */     if (keys != null) {
/* 182 */       for (JWK jwk : keys.getKeys()) {
/* 183 */         if (JWEAlgorithm.DIR.equals(jwk.getAlgorithm()) && KeyType.OCT.equals(jwk.getKeyType())) {
/* 184 */           return jwk.toOctetSequenceKey().toSecretKey("AES");
/*     */         }
/*     */       } 
/*     */     }
/* 188 */     return null;
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
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText) throws JOSEException {
/* 210 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/* 218 */     if (aad == null) {
/* 219 */       throw new JOSEException("Missing JWE additional authenticated data (AAD)");
/*     */     }
/*     */     
/* 222 */     EncryptionMethod enc = header.getEncryptionMethod();
/* 223 */     SecretKey cek = getCEK(enc);
/*     */ 
/*     */ 
/*     */     
/* 227 */     JWEHeader recipientHeader = null;
/* 228 */     Base64URL encryptedKey = null;
/* 229 */     Base64URL cipherText = null;
/* 230 */     Base64URL iv = null;
/* 231 */     Base64URL tag = null;
/*     */     
/* 233 */     Payload payload = new Payload(clearText);
/* 234 */     List<Object> recipients = JSONArrayUtils.newJSONArray();
/*     */     
/* 236 */     for (JWK key : this.keys.getKeys()) {
/* 237 */       JWEEncrypter encrypter; KeyType kty = key.getKeyType();
/*     */ 
/*     */       
/* 240 */       Map<String, Object> keyMap = key.toJSONObject();
/* 241 */       UnprotectedHeader.Builder unprotected = new UnprotectedHeader.Builder();
/* 242 */       for (String param : RECIPIENT_HEADER_PARAMS) {
/* 243 */         if (keyMap.containsKey(param)) {
/* 244 */           unprotected.param(param, keyMap.get(param));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 250 */         recipientHeader = (JWEHeader)header.join(unprotected.build());
/* 251 */       } catch (Exception e) {
/* 252 */         throw new JOSEException(e.getMessage(), e);
/*     */       } 
/* 254 */       JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(recipientHeader);
/*     */       
/* 256 */       if (KeyType.RSA.equals(kty) && RSAEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 257 */         encrypter = new RSAEncrypter(key.toRSAKey().toRSAPublicKey(), cek);
/* 258 */       } else if (KeyType.EC.equals(kty) && ECDHEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 259 */         encrypter = new ECDHEncrypter(key.toECKey().toECPublicKey(), cek);
/* 260 */       } else if (KeyType.OCT.equals(kty) && AESEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 261 */         encrypter = new AESEncrypter(key.toOctetSequenceKey().toSecretKey("AES"), cek);
/* 262 */       } else if (KeyType.OCT.equals(kty) && DirectEncrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 263 */         encrypter = new DirectEncrypter(key.toOctetSequenceKey().toSecretKey("AES"));
/* 264 */       } else if (KeyType.OKP.equals(kty) && X25519Encrypter.SUPPORTED_ALGORITHMS.contains(alg)) {
/* 265 */         encrypter = new X25519Encrypter(key.toOctetKeyPair().toPublicJWK(), cek);
/*     */       } else {
/*     */         continue;
/*     */       } 
/* 269 */       JWECryptoParts jweParts = encrypter.encrypt(recipientHeader, payload.toBytes(), aad);
/*     */ 
/*     */       
/* 272 */       Map<String, Object> recipientHeaderMap = jweParts.getHeader().toJSONObject();
/* 273 */       for (String param : header.getIncludedParams()) {
/* 274 */         recipientHeaderMap.remove(param);
/*     */       }
/* 276 */       Map<String, Object> recipient = JSONObjectUtils.newJSONObject();
/* 277 */       recipient.put("header", recipientHeaderMap);
/*     */ 
/*     */       
/* 280 */       if (!JWEAlgorithm.DIR.equals(alg)) {
/* 281 */         recipient.put("encrypted_key", jweParts.getEncryptedKey().toString());
/*     */       }
/* 283 */       recipients.add(recipient);
/*     */ 
/*     */       
/* 286 */       if (recipients.size() == 1) {
/* 287 */         payload = new Payload("");
/* 288 */         encryptedKey = jweParts.getEncryptedKey();
/* 289 */         iv = jweParts.getInitializationVector();
/* 290 */         cipherText = jweParts.getCipherText();
/* 291 */         tag = jweParts.getAuthenticationTag();
/*     */       } 
/*     */     } 
/* 294 */     if (recipients.size() > 1) {
/* 295 */       Map<String, Object> jweJsonObject = JSONObjectUtils.newJSONObject();
/* 296 */       jweJsonObject.put("recipients", recipients);
/* 297 */       encryptedKey = Base64URL.encode(JSONObjectUtils.toJSONString(jweJsonObject));
/*     */     } 
/* 299 */     return new JWECryptoParts(header, encryptedKey, iv, cipherText, tag);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\MultiEncrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */