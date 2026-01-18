/*     */ package com.nimbusds.jose.jca;
/*     */ 
/*     */ import com.nimbusds.jose.Algorithm;
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.crypto.impl.ECDSA;
/*     */ import com.nimbusds.jose.crypto.impl.RSASSA;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.Mac;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JCASupport
/*     */ {
/*     */   public static boolean isUnlimitedStrength() {
/*     */     try {
/*  56 */       return (Cipher.getMaxAllowedKeyLength("AES") >= 256);
/*  57 */     } catch (NoSuchAlgorithmException e) {
/*  58 */       return false;
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
/*     */   public static boolean isSupported(Algorithm alg) {
/*  74 */     if (alg instanceof JWSAlgorithm) {
/*  75 */       return isSupported((JWSAlgorithm)alg);
/*     */     }
/*  77 */     if (alg instanceof JWEAlgorithm) {
/*  78 */       return isSupported((JWEAlgorithm)alg);
/*     */     }
/*  80 */     if (alg instanceof EncryptionMethod) {
/*  81 */       return isSupported((EncryptionMethod)alg);
/*     */     }
/*  83 */     throw new IllegalArgumentException("Unexpected algorithm class: " + alg.getClass().getCanonicalName());
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
/*     */   public static boolean isSupported(Algorithm alg, Provider provider) {
/*  99 */     if (alg instanceof JWSAlgorithm) {
/* 100 */       return isSupported((JWSAlgorithm)alg, provider);
/*     */     }
/* 102 */     if (alg instanceof JWEAlgorithm) {
/* 103 */       return isSupported((JWEAlgorithm)alg, provider);
/*     */     }
/* 105 */     if (alg instanceof EncryptionMethod) {
/* 106 */       return isSupported((EncryptionMethod)alg, provider);
/*     */     }
/* 108 */     throw new IllegalArgumentException("Unexpected algorithm class: " + alg.getClass().getCanonicalName());
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
/*     */   public static boolean isSupported(JWSAlgorithm alg) {
/* 123 */     if (alg.getName().equals(Algorithm.NONE.getName())) {
/* 124 */       return true;
/*     */     }
/*     */     
/* 127 */     for (Provider p : Security.getProviders()) {
/*     */       
/* 129 */       if (isSupported(alg, p)) {
/* 130 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return false;
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
/*     */   public static boolean isSupported(JWSAlgorithm alg, Provider provider) {
/* 150 */     if (JWSAlgorithm.Family.HMAC_SHA.contains(alg)) {
/*     */       String jcaName;
/* 152 */       if (alg.equals(JWSAlgorithm.HS256)) {
/* 153 */         jcaName = "HMACSHA256";
/* 154 */       } else if (alg.equals(JWSAlgorithm.HS384)) {
/* 155 */         jcaName = "HMACSHA384";
/* 156 */       } else if (alg.equals(JWSAlgorithm.HS512)) {
/* 157 */         jcaName = "HMACSHA512";
/*     */       } else {
/* 159 */         return false;
/*     */       } 
/*     */       try {
/* 162 */         Mac.getInstance(jcaName, provider);
/* 163 */       } catch (NoSuchAlgorithmException e) {
/* 164 */         return false;
/*     */       } 
/* 166 */       return true;
/*     */     } 
/*     */     
/* 169 */     if (JWSAlgorithm.Family.RSA.contains(alg)) {
/*     */       try {
/* 171 */         RSASSA.getSignerAndVerifier(alg, provider);
/* 172 */       } catch (JOSEException e) {
/* 173 */         String jcaName; return false;
/*     */       } 
/* 175 */       return true;
/*     */     } 
/*     */     
/* 178 */     if (JWSAlgorithm.Family.EC.contains(alg)) {
/*     */       try {
/* 180 */         ECDSA.getSignerAndVerifier(alg, provider);
/* 181 */       } catch (JOSEException e) {
/* 182 */         return false;
/*     */       } 
/* 184 */       return true;
/*     */     } 
/*     */     
/* 187 */     return false;
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
/*     */   public static boolean isSupported(JWEAlgorithm alg) {
/* 202 */     for (Provider p : Security.getProviders()) {
/*     */       
/* 204 */       if (isSupported(alg, p)) {
/* 205 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 209 */     return false;
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
/*     */   public static boolean isSupported(JWEAlgorithm alg, Provider provider) {
/* 227 */     if (JWEAlgorithm.Family.RSA.contains(alg)) {
/* 228 */       String jcaName; if (alg.equals(JWEAlgorithm.RSA1_5)) {
/* 229 */         jcaName = "RSA/ECB/PKCS1Padding";
/* 230 */       } else if (alg.equals(JWEAlgorithm.RSA_OAEP)) {
/* 231 */         jcaName = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
/* 232 */       } else if (alg.equals(JWEAlgorithm.RSA_OAEP_256)) {
/* 233 */         jcaName = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
/* 234 */       } else if (alg.equals(JWEAlgorithm.RSA_OAEP_512)) {
/* 235 */         jcaName = "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
/*     */       } else {
/* 237 */         return false;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 242 */         Cipher.getInstance(jcaName, provider);
/* 243 */       } catch (NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException e) {
/* 244 */         return false;
/*     */       } 
/* 246 */       return true;
/*     */     } 
/*     */     
/* 249 */     if (JWEAlgorithm.Family.AES_KW.contains(alg)) {
/* 250 */       return (provider.getService("Cipher", "AESWrap") != null);
/*     */     }
/*     */     
/* 253 */     if (JWEAlgorithm.Family.ECDH_ES.contains(alg)) {
/* 254 */       return (provider.getService("KeyAgreement", "ECDH") != null);
/*     */     }
/*     */     
/* 257 */     if (JWEAlgorithm.Family.AES_GCM_KW.contains(alg)) {
/*     */       
/*     */       try {
/* 260 */         Cipher.getInstance("AES/GCM/NoPadding", provider);
/* 261 */       } catch (NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException e) {
/* 262 */         return false;
/*     */       } 
/* 264 */       return true;
/*     */     } 
/*     */     
/* 267 */     if (JWEAlgorithm.Family.PBES2.contains(alg)) {
/*     */       String hmac;
/* 269 */       if (alg.equals(JWEAlgorithm.PBES2_HS256_A128KW)) {
/* 270 */         hmac = "HmacSHA256";
/* 271 */       } else if (alg.equals(JWEAlgorithm.PBES2_HS384_A192KW)) {
/* 272 */         hmac = "HmacSHA384";
/*     */       } else {
/* 274 */         hmac = "HmacSHA512";
/*     */       } 
/* 276 */       return (provider.getService("KeyGenerator", hmac) != null);
/*     */     } 
/*     */     
/* 279 */     return JWEAlgorithm.DIR.equals(alg);
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
/*     */   public static boolean isSupported(EncryptionMethod enc) {
/* 294 */     for (Provider p : Security.getProviders()) {
/*     */       
/* 296 */       if (isSupported(enc, p)) {
/* 297 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return false;
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
/*     */   public static boolean isSupported(EncryptionMethod enc, Provider provider) {
/* 317 */     if (EncryptionMethod.Family.AES_CBC_HMAC_SHA.contains(enc)) {
/*     */       String hmac;
/*     */       try {
/* 320 */         Cipher.getInstance("AES/CBC/PKCS5Padding", provider);
/* 321 */       } catch (NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException e) {
/* 322 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 326 */       if (enc.equals(EncryptionMethod.A128CBC_HS256)) {
/* 327 */         hmac = "HmacSHA256";
/* 328 */       } else if (enc.equals(EncryptionMethod.A192CBC_HS384)) {
/* 329 */         hmac = "HmacSHA384";
/*     */       } else {
/* 331 */         hmac = "HmacSHA512";
/*     */       } 
/* 333 */       return (provider.getService("KeyGenerator", hmac) != null);
/*     */     } 
/*     */     
/* 336 */     if (EncryptionMethod.Family.AES_GCM.contains(enc)) {
/*     */       
/*     */       try {
/* 339 */         Cipher.getInstance("AES/GCM/NoPadding", provider);
/* 340 */       } catch (NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException e) {
/* 341 */         return false;
/*     */       } 
/* 343 */       return true;
/*     */     } 
/*     */     
/* 346 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jca\JCASupport.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */