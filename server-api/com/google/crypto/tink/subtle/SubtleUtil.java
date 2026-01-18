/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SubtleUtil
/*     */ {
/*     */   public static String toEcdsaAlgo(Enums.HashType hash) throws GeneralSecurityException {
/*  40 */     Validators.validateSignatureHash(hash);
/*  41 */     return hash + "withECDSA";
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
/*     */   public static String toRsaSsaPkcs1Algo(Enums.HashType hash) throws GeneralSecurityException {
/*  54 */     Validators.validateSignatureHash(hash);
/*  55 */     return hash + "withRSA";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toDigestAlgo(Enums.HashType hash) throws GeneralSecurityException {
/*  66 */     switch (hash) {
/*     */       case SHA1:
/*  68 */         return "SHA-1";
/*     */       case SHA224:
/*  70 */         return "SHA-224";
/*     */       case SHA256:
/*  72 */         return "SHA-256";
/*     */       case SHA384:
/*  74 */         return "SHA-384";
/*     */       case SHA512:
/*  76 */         return "SHA-512";
/*     */     } 
/*  78 */     throw new GeneralSecurityException("Unsupported hash " + hash);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAndroid() {
/*  88 */     return "The Android Project".equals(System.getProperty("java.vendor"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int androidApiLevel() {
/*  99 */     Integer androidApiLevel = Util.getAndroidApiLevel();
/* 100 */     if (androidApiLevel != null) {
/* 101 */       return androidApiLevel.intValue();
/*     */     }
/* 103 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigInteger bytes2Integer(byte[] bs) {
/* 114 */     return BigIntegerEncoding.fromUnsignedBigEndianBytes(bs);
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
/*     */   public static byte[] integer2Bytes(BigInteger num, int intendedLength) throws GeneralSecurityException {
/* 127 */     return BigIntegerEncoding.toBigEndianBytesOfFixedLength(num, intendedLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] mgf1(byte[] mgfSeed, int maskLen, Enums.HashType mgfHash) throws GeneralSecurityException {
/* 134 */     MessageDigest digest = EngineFactory.MESSAGE_DIGEST.getInstance(toDigestAlgo(mgfHash));
/* 135 */     int hLen = digest.getDigestLength();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     byte[] t = new byte[maskLen];
/* 141 */     int tPos = 0;
/* 142 */     for (int counter = 0; counter <= (maskLen - 1) / hLen; counter++) {
/* 143 */       digest.reset();
/* 144 */       digest.update(mgfSeed);
/* 145 */       digest.update(integer2Bytes(BigInteger.valueOf(counter), 4));
/* 146 */       byte[] c = digest.digest();
/* 147 */       System.arraycopy(c, 0, t, tPos, Math.min(c.length, t.length - tPos));
/* 148 */       tPos += c.length;
/*     */     } 
/* 150 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putAsUnsigedInt(ByteBuffer buffer, long value) throws GeneralSecurityException {
/* 160 */     if (0L > value || value >= 4294967296L) {
/* 161 */       throw new GeneralSecurityException("Index out of range");
/*     */     }
/* 163 */     buffer.putInt((int)value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\SubtleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */