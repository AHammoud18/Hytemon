/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AESCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.AESGCM;
/*     */ import com.nimbusds.jose.crypto.impl.AESGCMKW;
/*     */ import com.nimbusds.jose.crypto.impl.AESKW;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.AuthenticatedCipherText;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.jwk.OctetSequenceKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.Container;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class AESEncrypter
/*     */   extends AESCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   private enum AlgFamily
/*     */   {
/*  87 */     AESKW, AESGCMKW;
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
/*     */   public AESEncrypter(SecretKey kek, SecretKey contentEncryptionKey) throws KeyLengthException {
/* 110 */     super(kek, contentEncryptionKey);
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
/*     */   public AESEncrypter(SecretKey kek) throws KeyLengthException {
/* 126 */     this(kek, (SecretKey)null);
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
/*     */   public AESEncrypter(byte[] keyBytes) throws KeyLengthException {
/* 141 */     this(new SecretKeySpec(keyBytes, "AES"));
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
/*     */   public AESEncrypter(OctetSequenceKey octJWK) throws KeyLengthException {
/* 158 */     this(octJWK.toSecretKey("AES"));
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
/* 180 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/*     */     AlgFamily algFamily;
/*     */     JWEHeader updatedHeader;
/*     */     Base64URL encryptedKey;
/* 188 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 189 */     EncryptionMethod enc = header.getEncryptionMethod();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (alg.equals(JWEAlgorithm.A128KW)) {
/*     */       
/* 196 */       if (ByteUtils.safeBitLength(getKey().getEncoded()) != 128) {
/* 197 */         throw new KeyLengthException("The Key Encryption Key (KEK) length must be 128 bits for A128KW encryption");
/*     */       }
/* 199 */       algFamily = AlgFamily.AESKW;
/*     */     }
/* 201 */     else if (alg.equals(JWEAlgorithm.A192KW)) {
/*     */       
/* 203 */       if (ByteUtils.safeBitLength(getKey().getEncoded()) != 192) {
/* 204 */         throw new KeyLengthException("The Key Encryption Key (KEK) length must be 192 bits for A192KW encryption");
/*     */       }
/* 206 */       algFamily = AlgFamily.AESKW;
/*     */     }
/* 208 */     else if (alg.equals(JWEAlgorithm.A256KW)) {
/*     */       
/* 210 */       if (ByteUtils.safeBitLength(getKey().getEncoded()) != 256) {
/* 211 */         throw new KeyLengthException("The Key Encryption Key (KEK) length must be 256 bits for A256KW encryption");
/*     */       }
/* 213 */       algFamily = AlgFamily.AESKW;
/*     */     }
/* 215 */     else if (alg.equals(JWEAlgorithm.A128GCMKW)) {
/*     */       
/* 217 */       if (ByteUtils.safeBitLength(getKey().getEncoded()) != 128) {
/* 218 */         throw new KeyLengthException("The Key Encryption Key (KEK) length must be 128 bits for A128GCMKW encryption");
/*     */       }
/* 220 */       algFamily = AlgFamily.AESGCMKW;
/*     */     }
/* 222 */     else if (alg.equals(JWEAlgorithm.A192GCMKW)) {
/*     */       
/* 224 */       if (ByteUtils.safeBitLength(getKey().getEncoded()) != 192) {
/* 225 */         throw new KeyLengthException("The Key Encryption Key (KEK) length must be 192 bits for A192GCMKW encryption");
/*     */       }
/* 227 */       algFamily = AlgFamily.AESGCMKW;
/*     */     }
/* 229 */     else if (alg.equals(JWEAlgorithm.A256GCMKW)) {
/*     */       
/* 231 */       if (ByteUtils.safeBitLength(getKey().getEncoded()) != 256) {
/* 232 */         throw new KeyLengthException("The Key Encryption Key (KEK) length must be 256 bits for A256GCMKW encryption");
/*     */       }
/* 234 */       algFamily = AlgFamily.AESGCMKW;
/*     */     }
/*     */     else {
/*     */       
/* 238 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, SUPPORTED_ALGORITHMS));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     SecretKey cek = getCEK(enc);
/*     */     
/* 246 */     if (AlgFamily.AESKW.equals(algFamily)) {
/*     */       
/* 248 */       encryptedKey = Base64URL.encode(AESKW.wrapCEK(cek, getKey(), getJCAContext().getKeyEncryptionProvider()));
/* 249 */       updatedHeader = header;
/*     */     } else {
/*     */       
/* 252 */       assert AlgFamily.AESGCMKW.equals(algFamily);
/*     */       
/* 254 */       Container<byte[]> keyIV = new Container(AESGCM.generateIV(getJCAContext().getSecureRandom()));
/* 255 */       AuthenticatedCipherText authCiphCEK = AESGCMKW.encryptCEK(cek, keyIV, getKey(), getJCAContext().getKeyEncryptionProvider());
/* 256 */       encryptedKey = Base64URL.encode(authCiphCEK.getCipherText());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       updatedHeader = (new JWEHeader.Builder(header)).iv(Base64URL.encode((byte[])keyIV.get())).authTag(Base64URL.encode(authCiphCEK.getAuthenticationTag())).build();
/*     */     } 
/*     */ 
/*     */     
/* 266 */     byte[] updatedAAD = Arrays.equals(AAD.compute(header), aad) ? AAD.compute(updatedHeader) : aad;
/*     */     
/* 268 */     return ContentCryptoProvider.encrypt(updatedHeader, clearText, updatedAAD, cek, encryptedKey, getJCAContext());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\AESEncrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */