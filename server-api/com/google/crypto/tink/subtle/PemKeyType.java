/*     */ package com.google.crypto.tink.subtle;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public enum PemKeyType
/*     */ {
/*  41 */   RSA_PSS_2048_SHA256("RSA", "RSASSA-PSS", 2048, Enums.HashType.SHA256),
/*     */   
/*  43 */   RSA_PSS_3072_SHA256("RSA", "RSASSA-PSS", 3072, Enums.HashType.SHA256),
/*     */   
/*  45 */   RSA_PSS_4096_SHA256("RSA", "RSASSA-PSS", 4096, Enums.HashType.SHA256),
/*     */   
/*  47 */   RSA_PSS_4096_SHA512("RSA", "RSASSA-PSS", 4096, Enums.HashType.SHA512),
/*     */ 
/*     */   
/*  50 */   RSA_SIGN_PKCS1_2048_SHA256("RSA", "RSASSA-PKCS1-v1_5", 2048, Enums.HashType.SHA256),
/*     */   
/*  52 */   RSA_SIGN_PKCS1_3072_SHA256("RSA", "RSASSA-PKCS1-v1_5", 3072, Enums.HashType.SHA256),
/*     */   
/*  54 */   RSA_SIGN_PKCS1_4096_SHA256("RSA", "RSASSA-PKCS1-v1_5", 4096, Enums.HashType.SHA256),
/*     */   
/*  56 */   RSA_SIGN_PKCS1_4096_SHA512("RSA", "RSASSA-PKCS1-v1_5", 4096, Enums.HashType.SHA512),
/*     */ 
/*     */   
/*  59 */   ECDSA_P256_SHA256("EC", "ECDSA", 256, Enums.HashType.SHA256),
/*     */   
/*  61 */   ECDSA_P384_SHA384("EC", "ECDSA", 384, Enums.HashType.SHA384),
/*     */   
/*  63 */   ECDSA_P521_SHA512("EC", "ECDSA", 521, Enums.HashType.SHA512);
/*     */   
/*     */   public final String keyType;
/*     */   public final String algorithm;
/*     */   public final int keySizeInBits;
/*     */   public final Enums.HashType hash;
/*     */   
/*     */   PemKeyType(String keyType, String algorithm, int keySizeInBits, Enums.HashType hash) {
/*  71 */     this.keyType = keyType;
/*  72 */     this.algorithm = algorithm;
/*  73 */     this.keySizeInBits = keySizeInBits;
/*  74 */     this.hash = hash;
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
/*  90 */     String line = reader.readLine();
/*  91 */     while (line != null && !line.startsWith("-----BEGIN ")) {
/*  92 */       line = reader.readLine();
/*     */     }
/*  94 */     if (line == null) {
/*  95 */       return null;
/*     */     }
/*     */     
/*  98 */     line = line.trim().substring("-----BEGIN ".length());
/*  99 */     int index = line.indexOf("-----");
/* 100 */     if (index < 0) {
/* 101 */       return null;
/*     */     }
/* 103 */     String type = line.substring(0, index);
/* 104 */     String endMarker = "-----END " + type + "-----";
/* 105 */     StringBuilder base64key = new StringBuilder();
/*     */     
/* 107 */     while ((line = reader.readLine()) != null) {
/* 108 */       if (line.indexOf(":") > 0) {
/*     */         continue;
/*     */       }
/*     */       
/* 112 */       if (line.contains(endMarker)) {
/*     */         break;
/*     */       }
/* 115 */       base64key.append(line);
/*     */     } 
/*     */     try {
/* 118 */       byte[] key = Base64.decode(base64key.toString(), 0);
/* 119 */       if (type.contains("PUBLIC KEY"))
/* 120 */         return getPublicKey(key); 
/* 121 */       if (type.contains("PRIVATE KEY")) {
/* 122 */         return getPrivateKey(key);
/*     */       }
/* 124 */     } catch (GeneralSecurityException|IllegalArgumentException ex) {
/* 125 */       return null;
/*     */     } 
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   private Key getPublicKey(byte[] key) throws GeneralSecurityException {
/* 131 */     KeyFactory keyFactory = EngineFactory.KEY_FACTORY.getInstance(this.keyType);
/* 132 */     return validate(keyFactory.generatePublic(new X509EncodedKeySpec(key)));
/*     */   }
/*     */   
/*     */   private Key getPrivateKey(byte[] key) throws GeneralSecurityException {
/* 136 */     KeyFactory keyFactory = EngineFactory.KEY_FACTORY.getInstance(this.keyType);
/* 137 */     return validate(keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key)));
/*     */   }
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private Key validate(Key key) throws GeneralSecurityException {
/* 142 */     if (this.keyType.equals("RSA")) {
/* 143 */       RSAKey rsaKey = (RSAKey)key;
/* 144 */       int foundKeySizeInBits = rsaKey.getModulus().bitLength();
/* 145 */       if (foundKeySizeInBits != this.keySizeInBits)
/* 146 */         throw new GeneralSecurityException(
/* 147 */             String.format("invalid RSA key size, want %d got %d", new Object[] {
/* 148 */                 Integer.valueOf(this.keySizeInBits), Integer.valueOf(foundKeySizeInBits)
/*     */               })); 
/*     */     } else {
/* 151 */       ECKey ecKey = (ECKey)key;
/* 152 */       ECParameterSpec ecParams = ecKey.getParams();
/* 153 */       if (!EllipticCurves.isNistEcParameterSpec(ecParams)) {
/* 154 */         throw new GeneralSecurityException("unsupport EC spec: " + ecParams.toString());
/*     */       }
/*     */       
/* 157 */       int foundKeySizeInBits = EllipticCurves.fieldSizeInBits(ecParams.getCurve());
/* 158 */       if (foundKeySizeInBits != this.keySizeInBits)
/* 159 */         throw new GeneralSecurityException(
/* 160 */             String.format("invalid EC key size, want %d got %d", new Object[] {
/* 161 */                 Integer.valueOf(this.keySizeInBits), Integer.valueOf(foundKeySizeInBits)
/*     */               })); 
/*     */     } 
/* 164 */     return key;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\PemKeyType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */