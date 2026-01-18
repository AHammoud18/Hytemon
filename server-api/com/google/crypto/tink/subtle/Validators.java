/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Validators
/*     */ {
/*     */   private static final String TYPE_URL_PREFIX = "type.googleapis.com/";
/*     */   private static final int MIN_RSA_MODULUS_SIZE = 2048;
/*     */   private static final String URI_UNRESERVED_CHARS = "([0-9a-zA-Z\\-\\.\\_~])+";
/*     */   
/*     */   public static void validateTypeUrl(String typeUrl) throws GeneralSecurityException {
/*  46 */     if (!typeUrl.startsWith("type.googleapis.com/")) {
/*  47 */       throw new GeneralSecurityException(
/*  48 */           String.format("Error: type URL %s is invalid; it must start with %s.\n", new Object[] { typeUrl, "type.googleapis.com/" }));
/*     */     }
/*     */     
/*  51 */     if (typeUrl.length() == "type.googleapis.com/".length()) {
/*  52 */       throw new GeneralSecurityException(
/*  53 */           String.format("Error: type URL %s is invalid; it has no message name.\n", new Object[] { typeUrl }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateAesKeySize(int sizeInBytes) throws InvalidAlgorithmParameterException {
/*  59 */     if (sizeInBytes != 16 && sizeInBytes != 32) {
/*  60 */       throw new InvalidAlgorithmParameterException(
/*  61 */           String.format("invalid key size %d; only 128-bit and 256-bit AES keys are supported", new Object[] {
/*     */               
/*  63 */               Integer.valueOf(sizeInBytes * 8)
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateVersion(int candidate, int maxExpected) throws GeneralSecurityException {
/*  73 */     if (candidate < 0 || candidate > maxExpected) {
/*  74 */       throw new GeneralSecurityException(
/*  75 */           String.format("key has version %d; only keys with version in range [0..%d] are supported", new Object[] {
/*     */               
/*  77 */               Integer.valueOf(candidate), Integer.valueOf(maxExpected)
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateSignatureHash(Enums.HashType hash) throws GeneralSecurityException {
/*  88 */     switch (hash) {
/*     */       case SHA256:
/*     */       case SHA384:
/*     */       case SHA512:
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     throw new GeneralSecurityException("Unsupported hash: " + hash.name());
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
/*     */   public static void validateRsaModulusSize(int modulusSize) throws GeneralSecurityException {
/* 110 */     if (modulusSize < 2048) {
/* 111 */       throw new GeneralSecurityException(
/* 112 */           String.format("Modulus size is %d; only modulus size >= 2048-bit is supported", new Object[] {
/* 113 */               Integer.valueOf(modulusSize)
/*     */             }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (TinkFipsUtil.useOnlyFips() && 
/* 120 */       modulusSize != 2048 && modulusSize != 3072) {
/* 121 */       throw new GeneralSecurityException(
/* 122 */           String.format("Modulus size is %d; only modulus size of 2048- or 3072-bit is supported in FIPS mode.", new Object[] {
/*     */ 
/*     */               
/* 125 */               Integer.valueOf(modulusSize)
/*     */             }));
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
/*     */   public static void validateRsaPublicExponent(BigInteger publicExponent) throws GeneralSecurityException {
/* 143 */     if (!publicExponent.testBit(0)) {
/* 144 */       throw new GeneralSecurityException("Public exponent must be odd.");
/*     */     }
/*     */     
/* 147 */     if (publicExponent.compareTo(BigInteger.valueOf(65536L)) <= 0) {
/* 148 */       throw new GeneralSecurityException("Public exponent must be greater than 65536.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateNotExists(File f) throws IOException {
/* 156 */     if (f.exists()) {
/* 157 */       throw new IOException(String.format("%s exists, please choose another file\n", new Object[] { f }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateExists(File f) throws IOException {
/* 163 */     if (!f.exists()) {
/* 164 */       throw new IOException(
/* 165 */           String.format("Error: %s doesn't exist, please choose another file\n", new Object[] { f }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String validateKmsKeyUriAndRemovePrefix(String expectedPrefix, String kmsKeyUri) {
/* 175 */     if (!kmsKeyUri.toLowerCase(Locale.US).startsWith(expectedPrefix)) {
/* 176 */       throw new IllegalArgumentException(
/* 177 */           String.format("key URI must start with %s", new Object[] { expectedPrefix }));
/*     */     }
/* 179 */     return kmsKeyUri.substring(expectedPrefix.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   private static final Pattern GCP_KMS_CRYPTO_KEY_PATTERN = Pattern.compile(
/* 187 */       String.format("^projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s$", new Object[] { "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+" }), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   private static final Pattern GCP_KMS_CRYPTO_KEY_VERSION_PATTERN = Pattern.compile(
/* 197 */       String.format("^projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s/cryptoKeyVersions/%s$", new Object[] { "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+", "([0-9a-zA-Z\\-\\.\\_~])+" }), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateCryptoKeyUri(String kmsKeyUri) throws GeneralSecurityException {
/* 210 */     if (!GCP_KMS_CRYPTO_KEY_PATTERN.matcher(kmsKeyUri).matches()) {
/* 211 */       if (GCP_KMS_CRYPTO_KEY_VERSION_PATTERN.matcher(kmsKeyUri).matches()) {
/* 212 */         throw new GeneralSecurityException("Invalid Google Cloud KMS Key URI. The URI must point to a CryptoKey, not a CryptoKeyVersion");
/*     */       }
/*     */ 
/*     */       
/* 216 */       throw new GeneralSecurityException("Invalid Google Cloud KMS Key URI. The URI must point to a CryptoKey in the format projects/*/locations/*/keyRings/*/cryptoKeys/*. See https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys#CryptoKey");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Validators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */