/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.Enums;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.interfaces.ECKey;
/*     */ import java.security.interfaces.RSAKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum PemKeyType
/*     */ {
/*  39 */   RSA_PSS_2048_SHA256("RSA", "RSASSA-PSS", 2048, Enums.HashType.SHA256),
/*     */   
/*  41 */   RSA_PSS_3072_SHA256("RSA", "RSASSA-PSS", 3072, Enums.HashType.SHA256),
/*     */   
/*  43 */   RSA_PSS_4096_SHA256("RSA", "RSASSA-PSS", 4096, Enums.HashType.SHA256),
/*     */   
/*  45 */   RSA_PSS_4096_SHA512("RSA", "RSASSA-PSS", 4096, Enums.HashType.SHA512),
/*     */ 
/*     */   
/*  48 */   RSA_SIGN_PKCS1_2048_SHA256("RSA", "RSASSA-PKCS1-v1_5", 2048, Enums.HashType.SHA256),
/*     */   
/*  50 */   RSA_SIGN_PKCS1_3072_SHA256("RSA", "RSASSA-PKCS1-v1_5", 3072, Enums.HashType.SHA256),
/*     */   
/*  52 */   RSA_SIGN_PKCS1_4096_SHA256("RSA", "RSASSA-PKCS1-v1_5", 4096, Enums.HashType.SHA256),
/*     */   
/*  54 */   RSA_SIGN_PKCS1_4096_SHA512("RSA", "RSASSA-PKCS1-v1_5", 4096, Enums.HashType.SHA512),
/*     */ 
/*     */   
/*  57 */   ECDSA_P256_SHA256("EC", "ECDSA", 256, Enums.HashType.SHA256),
/*     */   
/*  59 */   ECDSA_P384_SHA384("EC", "ECDSA", 384, Enums.HashType.SHA384),
/*     */   
/*  61 */   ECDSA_P521_SHA512("EC", "ECDSA", 521, Enums.HashType.SHA512);
/*     */   
/*     */   public final String keyType;
/*     */   public final String algorithm;
/*     */   public final int keySizeInBits;
/*     */   public final Enums.HashType hash;
/*     */   
/*     */   PemKeyType(String keyType, String algorithm, int keySizeInBits, Enums.HashType hash) {
/*  69 */     this.keyType = keyType;
/*  70 */     this.algorithm = algorithm;
/*  71 */     this.keySizeInBits = keySizeInBits;
/*  72 */     this.hash = hash;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String PUBLIC_KEY = "PUBLIC KEY";
/*     */   
/*     */   private static final String PRIVATE_KEY = "PRIVATE KEY";
/*     */   
/*     */   private static final String BEGIN = "-----BEGIN ";
/*     */   
/*     */   private static final String END = "-----END ";
/*     */   
/*     */   private static final String MARKER = "-----";
/*     */   
/*     */   @Nullable
/*     */   public Key readKey(BufferedReader reader) throws IOException {
/*  88 */     String line = reader.readLine();
/*  89 */     while (line != null && !line.startsWith("-----BEGIN ")) {
/*  90 */       line = reader.readLine();
/*     */     }
/*  92 */     if (line == null) {
/*  93 */       return null;
/*     */     }
/*     */     
/*  96 */     line = line.trim().substring("-----BEGIN ".length());
/*  97 */     int index = line.indexOf("-----");
/*  98 */     if (index < 0) {
/*  99 */       return null;
/*     */     }
/* 101 */     String type = line.substring(0, index);
/* 102 */     String endMarker = "-----END " + type + "-----";
/* 103 */     StringBuilder base64key = new StringBuilder();
/*     */     
/* 105 */     while ((line = reader.readLine()) != null) {
/* 106 */       if (line.indexOf(":") > 0) {
/*     */         continue;
/*     */       }
/*     */       
/* 110 */       if (line.contains(endMarker)) {
/*     */         break;
/*     */       }
/* 113 */       base64key.append(line);
/*     */     } 
/*     */     try {
/* 116 */       byte[] key = Base64.decode(base64key.toString(), 0);
/* 117 */       if (type.contains("PUBLIC KEY"))
/* 118 */         return getPublicKey(key); 
/* 119 */       if (type.contains("PRIVATE KEY")) {
/* 120 */         return getPrivateKey(key);
/*     */       }
/* 122 */     } catch (GeneralSecurityException|IllegalArgumentException ex) {
/* 123 */       return null;
/*     */     } 
/* 125 */     return null;
/*     */   }
/*     */   
/*     */   private Key getPublicKey(byte[] key) throws GeneralSecurityException {
/* 129 */     KeyFactory keyFactory = (KeyFactory)EngineFactory.KEY_FACTORY.getInstance(this.keyType);
/* 130 */     return validate(keyFactory.generatePublic(new X509EncodedKeySpec(key)));
/*     */   }
/*     */   
/*     */   private Key getPrivateKey(byte[] key) throws GeneralSecurityException {
/* 134 */     KeyFactory keyFactory = (KeyFactory)EngineFactory.KEY_FACTORY.getInstance(this.keyType);
/* 135 */     return validate(keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key)));
/*     */   }
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private Key validate(Key key) throws GeneralSecurityException {
/* 140 */     if (this.keyType.equals("RSA")) {
/* 141 */       RSAKey rsaKey = (RSAKey)key;
/* 142 */       int foundKeySizeInBits = rsaKey.getModulus().bitLength();
/* 143 */       if (foundKeySizeInBits != this.keySizeInBits)
/* 144 */         throw new GeneralSecurityException(
/* 145 */             String.format("invalid RSA key size, want %d got %d", new Object[] {
/* 146 */                 Integer.valueOf(this.keySizeInBits), Integer.valueOf(foundKeySizeInBits)
/*     */               })); 
/*     */     } else {
/* 149 */       ECKey ecKey = (ECKey)key;
/* 150 */       ECParameterSpec ecParams = ecKey.getParams();
/* 151 */       if (!EllipticCurves.isNistEcParameterSpec(ecParams)) {
/* 152 */         throw new GeneralSecurityException("unsupport EC spec: " + ecParams.toString());
/*     */       }
/*     */       
/* 155 */       int foundKeySizeInBits = EllipticCurves.fieldSizeInBits(ecParams.getCurve());
/* 156 */       if (foundKeySizeInBits != this.keySizeInBits)
/* 157 */         throw new GeneralSecurityException(
/* 158 */             String.format("invalid EC key size, want %d got %d", new Object[] {
/* 159 */                 Integer.valueOf(this.keySizeInBits), Integer.valueOf(foundKeySizeInBits)
/*     */               })); 
/*     */     } 
/* 162 */     return key;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\PemKeyType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */