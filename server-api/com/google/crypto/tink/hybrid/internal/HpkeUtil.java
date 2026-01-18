/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import java.security.GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HpkeUtil
/*     */ {
/*  29 */   public static final byte[] BASE_MODE = intToByteArray(1, 0);
/*  30 */   public static final byte[] AUTH_MODE = intToByteArray(1, 2);
/*     */ 
/*     */   
/*  33 */   public static final byte[] X25519_HKDF_SHA256_KEM_ID = intToByteArray(2, 32);
/*  34 */   public static final byte[] P256_HKDF_SHA256_KEM_ID = intToByteArray(2, 16);
/*  35 */   public static final byte[] P384_HKDF_SHA384_KEM_ID = intToByteArray(2, 17);
/*  36 */   public static final byte[] P521_HKDF_SHA512_KEM_ID = intToByteArray(2, 18);
/*     */ 
/*     */   
/*  39 */   public static final byte[] HKDF_SHA256_KDF_ID = intToByteArray(2, 1);
/*  40 */   public static final byte[] HKDF_SHA384_KDF_ID = intToByteArray(2, 2);
/*  41 */   public static final byte[] HKDF_SHA512_KDF_ID = intToByteArray(2, 3);
/*     */ 
/*     */   
/*  44 */   public static final byte[] AES_128_GCM_AEAD_ID = intToByteArray(2, 1);
/*  45 */   public static final byte[] AES_256_GCM_AEAD_ID = intToByteArray(2, 2);
/*  46 */   public static final byte[] CHACHA20_POLY1305_AEAD_ID = intToByteArray(2, 3);
/*     */   
/*  48 */   public static final byte[] EMPTY_SALT = new byte[0];
/*     */   
/*  50 */   private static final byte[] KEM = "KEM".getBytes(Util.UTF_8);
/*  51 */   private static final byte[] HPKE = "HPKE".getBytes(Util.UTF_8);
/*  52 */   private static final byte[] HPKE_V1 = "HPKE-v1".getBytes(Util.UTF_8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] intToByteArray(int capacity, int value) {
/*  67 */     if (capacity > 4 || capacity < 0) {
/*  68 */       throw new IllegalArgumentException("capacity must be between 0 and 4");
/*     */     }
/*     */ 
/*     */     
/*  72 */     if (value < 0 || (capacity < 4 && value >= 1 << 8 * capacity)) {
/*  73 */       throw new IllegalArgumentException("value too large");
/*     */     }
/*  75 */     byte[] result = new byte[capacity];
/*  76 */     for (int i = 0; i < capacity; i++) {
/*  77 */       result[i] = (byte)(value >> 8 * (capacity - i - 1) & 0xFF);
/*     */     }
/*  79 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] kemSuiteId(byte[] kemId) throws GeneralSecurityException {
/*  89 */     return Bytes.concat(new byte[][] { KEM, kemId });
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
/*     */   static byte[] hpkeSuiteId(byte[] kemId, byte[] kdfId, byte[] aeadId) throws GeneralSecurityException {
/* 101 */     return Bytes.concat(new byte[][] { HPKE, kemId, kdfId, aeadId });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] labelIkm(String label, byte[] ikm, byte[] suiteId) throws GeneralSecurityException {
/* 111 */     return Bytes.concat(new byte[][] { HPKE_V1, suiteId, label.getBytes(Util.UTF_8), ikm });
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
/*     */   static byte[] labelInfo(String label, byte[] info, byte[] suiteId, int length) throws GeneralSecurityException {
/* 123 */     return Bytes.concat(new byte[][] { intToByteArray(2, length), HPKE_V1, suiteId, label.getBytes(Util.UTF_8), info });
/*     */   }
/*     */ 
/*     */   
/*     */   static EllipticCurves.CurveType nistHpkeKemToCurve(HpkeParameters.KemId kemId) throws GeneralSecurityException {
/* 128 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/* 129 */       return EllipticCurves.CurveType.NIST_P256;
/*     */     }
/* 131 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/* 132 */       return EllipticCurves.CurveType.NIST_P384;
/*     */     }
/* 134 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/* 135 */       return EllipticCurves.CurveType.NIST_P521;
/*     */     }
/* 137 */     throw new GeneralSecurityException("Unrecognized NIST HPKE KEM identifier");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEncodedPublicKeyLength(HpkeParameters.KemId kemId) throws GeneralSecurityException {
/* 143 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
/* 144 */       return 32;
/*     */     }
/* 146 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/* 147 */       return 65;
/*     */     }
/* 149 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/* 150 */       return 97;
/*     */     }
/* 152 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/* 153 */       return 133;
/*     */     }
/* 155 */     throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int encodingSizeInBytes(HpkeParameters.KemId kemId) {
/* 165 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
/* 166 */       return 32;
/*     */     }
/* 168 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/* 169 */       return 65;
/*     */     }
/* 171 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/* 172 */       return 97;
/*     */     }
/* 174 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/* 175 */       return 133;
/*     */     }
/* 177 */     throw new IllegalArgumentException("Unable to determine KEM-encoding length for " + kemId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEncodedPrivateKeyLength(HpkeParameters.KemId kemId) throws GeneralSecurityException {
/* 183 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
/* 184 */       return 32;
/*     */     }
/* 186 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/* 187 */       return 32;
/*     */     }
/* 189 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/* 190 */       return 48;
/*     */     }
/* 192 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/* 193 */       return 66;
/*     */     }
/* 195 */     throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */