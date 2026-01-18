/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BigIntegerEncoding
/*     */ {
/*     */   public static byte[] toBigEndianBytes(BigInteger n) {
/*  39 */     if (n.signum() == -1) {
/*  40 */       throw new IllegalArgumentException("n must not be negative");
/*     */     }
/*  42 */     return n.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] toUnsignedBigEndianBytes(BigInteger n) {
/*  52 */     if (n.signum() == -1) {
/*  53 */       throw new IllegalArgumentException("n must not be negative");
/*     */     }
/*  55 */     byte[] twosComplement = n.toByteArray();
/*  56 */     if (twosComplement[0] == 0) {
/*  57 */       return Arrays.copyOfRange(twosComplement, 1, twosComplement.length);
/*     */     }
/*  59 */     return twosComplement;
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
/*     */   public static byte[] toBigEndianBytesOfFixedLength(BigInteger n, int length) throws GeneralSecurityException {
/*  72 */     if (n.signum() == -1) {
/*  73 */       throw new IllegalArgumentException("integer must be nonnegative");
/*     */     }
/*  75 */     byte[] b = n.toByteArray();
/*  76 */     if (b.length == length) {
/*  77 */       return b;
/*     */     }
/*  79 */     if (b.length > length + 1) {
/*  80 */       throw new GeneralSecurityException("integer too large");
/*     */     }
/*  82 */     if (b.length == length + 1) {
/*  83 */       if (b[0] == 0) {
/*  84 */         return Arrays.copyOfRange(b, 1, b.length);
/*     */       }
/*  86 */       throw new GeneralSecurityException("integer too large");
/*     */     } 
/*     */ 
/*     */     
/*  90 */     byte[] res = new byte[length];
/*  91 */     System.arraycopy(b, 0, res, length - b.length, b.length);
/*  92 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigInteger fromUnsignedBigEndianBytes(byte[] bytes) {
/* 101 */     return new BigInteger(1, bytes);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\BigIntegerEncoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */