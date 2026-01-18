/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.jca.JWEJCAContext;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.Container;
/*     */ import com.nimbusds.jose.util.IntegerOverflowException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ContentCryptoProvider
/*     */ {
/*     */   public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS;
/*     */   public static final Map<Integer, Set<EncryptionMethod>> COMPATIBLE_ENCRYPTION_METHODS;
/*     */   
/*     */   static {
/*  56 */     Set<EncryptionMethod> methods = new LinkedHashSet<>();
/*  57 */     methods.add(EncryptionMethod.A128CBC_HS256);
/*  58 */     methods.add(EncryptionMethod.A192CBC_HS384);
/*  59 */     methods.add(EncryptionMethod.A256CBC_HS512);
/*  60 */     methods.add(EncryptionMethod.A128GCM);
/*  61 */     methods.add(EncryptionMethod.A192GCM);
/*  62 */     methods.add(EncryptionMethod.A256GCM);
/*  63 */     methods.add(EncryptionMethod.A128CBC_HS256_DEPRECATED);
/*  64 */     methods.add(EncryptionMethod.A256CBC_HS512_DEPRECATED);
/*  65 */     methods.add(EncryptionMethod.XC20P);
/*  66 */     SUPPORTED_ENCRYPTION_METHODS = Collections.unmodifiableSet(methods);
/*     */     
/*  68 */     Map<Integer, Set<EncryptionMethod>> encsMap = new HashMap<>();
/*  69 */     Set<EncryptionMethod> bit128Encs = new HashSet<>();
/*  70 */     Set<EncryptionMethod> bit192Encs = new HashSet<>();
/*  71 */     Set<EncryptionMethod> bit256Encs = new HashSet<>();
/*  72 */     Set<EncryptionMethod> bit384Encs = new HashSet<>();
/*  73 */     Set<EncryptionMethod> bit512Encs = new HashSet<>();
/*  74 */     bit128Encs.add(EncryptionMethod.A128GCM);
/*  75 */     bit192Encs.add(EncryptionMethod.A192GCM);
/*  76 */     bit256Encs.add(EncryptionMethod.A256GCM);
/*  77 */     bit256Encs.add(EncryptionMethod.A128CBC_HS256);
/*  78 */     bit256Encs.add(EncryptionMethod.A128CBC_HS256_DEPRECATED);
/*  79 */     bit256Encs.add(EncryptionMethod.XC20P);
/*  80 */     bit384Encs.add(EncryptionMethod.A192CBC_HS384);
/*  81 */     bit512Encs.add(EncryptionMethod.A256CBC_HS512);
/*  82 */     bit512Encs.add(EncryptionMethod.A256CBC_HS512_DEPRECATED);
/*  83 */     encsMap.put(Integer.valueOf(128), Collections.unmodifiableSet(bit128Encs));
/*  84 */     encsMap.put(Integer.valueOf(192), Collections.unmodifiableSet(bit192Encs));
/*  85 */     encsMap.put(Integer.valueOf(256), Collections.unmodifiableSet(bit256Encs));
/*  86 */     encsMap.put(Integer.valueOf(384), Collections.unmodifiableSet(bit384Encs));
/*  87 */     encsMap.put(Integer.valueOf(512), Collections.unmodifiableSet(bit512Encs));
/*  88 */     COMPATIBLE_ENCRYPTION_METHODS = Collections.unmodifiableMap(encsMap);
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
/*     */   public static SecretKey generateCEK(EncryptionMethod enc, SecureRandom randomGen) throws JOSEException {
/* 107 */     if (!SUPPORTED_ENCRYPTION_METHODS.contains(enc)) {
/* 108 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedEncryptionMethod(enc, SUPPORTED_ENCRYPTION_METHODS));
/*     */     }
/*     */     
/* 111 */     byte[] cekMaterial = new byte[ByteUtils.byteLength(enc.cekBitLength())];
/*     */     
/* 113 */     randomGen.nextBytes(cekMaterial);
/*     */     
/* 115 */     return new SecretKeySpec(cekMaterial, "AES");
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
/*     */   private static void checkCEKLength(SecretKey cek, EncryptionMethod enc) throws KeyLengthException {
/*     */     int cekBitLength;
/*     */     try {
/* 134 */       cekBitLength = ByteUtils.safeBitLength(cek.getEncoded());
/* 135 */     } catch (IntegerOverflowException e) {
/* 136 */       throw new KeyLengthException("The Content Encryption Key (CEK) is too long: " + e.getMessage());
/*     */     } 
/*     */     
/* 139 */     if (cekBitLength == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (enc.cekBitLength() != cekBitLength) {
/* 146 */       throw new KeyLengthException("The Content Encryption Key (CEK) length for " + enc + " must be " + enc.cekBitLength() + " bits");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JWECryptoParts encrypt(JWEHeader header, byte[] clearText, SecretKey cek, Base64URL encryptedKey, JWEJCAContext jcaProvider) throws JOSEException {
/* 174 */     return encrypt(header, clearText, null, cek, encryptedKey, jcaProvider);
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
/*     */   public static JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad, SecretKey cek, Base64URL encryptedKey, JWEJCAContext jcaProvider) throws JOSEException {
/*     */     byte[] iv;
/*     */     AuthenticatedCipherText authCipherText;
/* 205 */     if (aad == null)
/*     */     {
/* 207 */       return encrypt(header, clearText, AAD.compute(header), cek, encryptedKey, jcaProvider);
/*     */     }
/*     */     
/* 210 */     checkCEKLength(cek, header.getEncryptionMethod());
/*     */ 
/*     */     
/* 213 */     byte[] plainText = DeflateHelper.applyCompression(header, clearText);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (header.getEncryptionMethod().equals(EncryptionMethod.A128CBC_HS256) || header
/* 220 */       .getEncryptionMethod().equals(EncryptionMethod.A192CBC_HS384) || header
/* 221 */       .getEncryptionMethod().equals(EncryptionMethod.A256CBC_HS512)) {
/*     */       
/* 223 */       iv = AESCBC.generateIV(jcaProvider.getSecureRandom());
/*     */       
/* 225 */       authCipherText = AESCBC.encryptAuthenticated(cek, iv, plainText, aad, jcaProvider
/*     */           
/* 227 */           .getContentEncryptionProvider(), jcaProvider
/* 228 */           .getMACProvider());
/*     */     }
/* 230 */     else if (header.getEncryptionMethod().equals(EncryptionMethod.A128GCM) || header
/* 231 */       .getEncryptionMethod().equals(EncryptionMethod.A192GCM) || header
/* 232 */       .getEncryptionMethod().equals(EncryptionMethod.A256GCM)) {
/*     */       
/* 234 */       Container<byte[]> ivContainer = new Container(AESGCM.generateIV(jcaProvider.getSecureRandom()));
/*     */       
/* 236 */       authCipherText = AESGCM.encrypt(cek, ivContainer, plainText, aad, jcaProvider
/*     */           
/* 238 */           .getContentEncryptionProvider());
/*     */       
/* 240 */       iv = (byte[])ivContainer.get();
/*     */     }
/* 242 */     else if (header.getEncryptionMethod().equals(EncryptionMethod.A128CBC_HS256_DEPRECATED) || header
/* 243 */       .getEncryptionMethod().equals(EncryptionMethod.A256CBC_HS512_DEPRECATED)) {
/*     */       
/* 245 */       iv = AESCBC.generateIV(jcaProvider.getSecureRandom());
/*     */       
/* 247 */       authCipherText = AESCBC.encryptWithConcatKDF(header, cek, encryptedKey, iv, plainText, jcaProvider
/*     */           
/* 249 */           .getContentEncryptionProvider(), jcaProvider
/* 250 */           .getMACProvider());
/*     */     }
/* 252 */     else if (header.getEncryptionMethod().equals(EncryptionMethod.XC20P)) {
/*     */       
/* 254 */       Container<byte[]> ivContainer = new Container(null);
/*     */       
/* 256 */       authCipherText = XC20P.encryptAuthenticated(cek, ivContainer, plainText, aad);
/*     */       
/* 258 */       iv = (byte[])ivContainer.get();
/*     */     }
/*     */     else {
/*     */       
/* 262 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedEncryptionMethod(header
/* 263 */             .getEncryptionMethod(), SUPPORTED_ENCRYPTION_METHODS));
/*     */     } 
/*     */ 
/*     */     
/* 267 */     return new JWECryptoParts(header, encryptedKey, 
/*     */ 
/*     */         
/* 270 */         Base64URL.encode(iv), 
/* 271 */         Base64URL.encode(authCipherText.getCipherText()), 
/* 272 */         Base64URL.encode(authCipherText.getAuthenticationTag()));
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
/*     */   public static byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, SecretKey cek, JWEJCAContext jcaProvider) throws JOSEException {
/* 305 */     return decrypt(header, null, encryptedKey, iv, cipherText, authTag, cek, jcaProvider);
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
/*     */   
/*     */   public static byte[] decrypt(JWEHeader header, byte[] aad, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, SecretKey cek, JWEJCAContext jcaProvider) throws JOSEException {
/*     */     byte[] plainText;
/* 341 */     if (aad == null)
/*     */     {
/* 343 */       return decrypt(header, AAD.compute(header), encryptedKey, iv, cipherText, authTag, cek, jcaProvider);
/*     */     }
/*     */     
/* 346 */     checkCEKLength(cek, header.getEncryptionMethod());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     if (header.getEncryptionMethod().equals(EncryptionMethod.A128CBC_HS256) || header
/* 353 */       .getEncryptionMethod().equals(EncryptionMethod.A192CBC_HS384) || header
/* 354 */       .getEncryptionMethod().equals(EncryptionMethod.A256CBC_HS512)) {
/*     */       
/* 356 */       plainText = AESCBC.decryptAuthenticated(cek, iv
/*     */           
/* 358 */           .decode(), cipherText
/* 359 */           .decode(), aad, authTag
/*     */           
/* 361 */           .decode(), jcaProvider
/* 362 */           .getContentEncryptionProvider(), jcaProvider
/* 363 */           .getMACProvider());
/*     */     }
/* 365 */     else if (header.getEncryptionMethod().equals(EncryptionMethod.A128GCM) || header
/* 366 */       .getEncryptionMethod().equals(EncryptionMethod.A192GCM) || header
/* 367 */       .getEncryptionMethod().equals(EncryptionMethod.A256GCM)) {
/*     */       
/* 369 */       plainText = AESGCM.decrypt(cek, iv
/*     */           
/* 371 */           .decode(), cipherText
/* 372 */           .decode(), aad, authTag
/*     */           
/* 374 */           .decode(), jcaProvider
/* 375 */           .getContentEncryptionProvider());
/*     */     }
/* 377 */     else if (header.getEncryptionMethod().equals(EncryptionMethod.A128CBC_HS256_DEPRECATED) || header
/* 378 */       .getEncryptionMethod().equals(EncryptionMethod.A256CBC_HS512_DEPRECATED)) {
/*     */       
/* 380 */       plainText = AESCBC.decryptWithConcatKDF(header, cek, encryptedKey, iv, cipherText, authTag, jcaProvider
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 387 */           .getContentEncryptionProvider(), jcaProvider
/* 388 */           .getMACProvider());
/*     */     }
/* 390 */     else if (header.getEncryptionMethod().equals(EncryptionMethod.XC20P)) {
/*     */       
/* 392 */       plainText = XC20P.decryptAuthenticated(cek, iv
/*     */           
/* 394 */           .decode(), cipherText
/* 395 */           .decode(), aad, authTag
/*     */           
/* 397 */           .decode());
/*     */     }
/*     */     else {
/*     */       
/* 401 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedEncryptionMethod(header
/* 402 */             .getEncryptionMethod(), SUPPORTED_ENCRYPTION_METHODS));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     return DeflateHelper.applyDecompression(header, plainText);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\ContentCryptoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */