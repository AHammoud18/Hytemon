/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Ed25519Constants
/*     */ {
/*     */   static final long[] D;
/*     */   static final long[] D2;
/*     */   static final long[] SQRTM1;
/*     */   static final Ed25519.CachedXYT[][] B_TABLE;
/*     */   static final Ed25519.CachedXYT[] B2;
/*  41 */   private static final BigInteger P_BI = BigInteger.valueOf(2L).pow(255).subtract(BigInteger.valueOf(19L));
/*     */   
/*  43 */   private static final BigInteger D_BI = BigInteger.valueOf(-121665L).multiply(BigInteger.valueOf(121666L).modInverse(P_BI)).mod(P_BI);
/*  44 */   private static final BigInteger D2_BI = BigInteger.valueOf(2L).multiply(D_BI).mod(P_BI);
/*     */   
/*  46 */   private static final BigInteger SQRTM1_BI = BigInteger.valueOf(2L).modPow(P_BI.subtract(BigInteger.ONE).divide(BigInteger.valueOf(4L)), P_BI);
/*     */ 
/*     */   
/*     */   private static class Point
/*     */   {
/*     */     private BigInteger x;
/*     */     private BigInteger y;
/*     */     
/*     */     private Point() {}
/*     */   }
/*     */   
/*     */   private static BigInteger recoverX(BigInteger y) {
/*  58 */     BigInteger xx = y.pow(2).subtract(BigInteger.ONE).multiply(D_BI.multiply(y.pow(2)).add(BigInteger.ONE).modInverse(P_BI));
/*  59 */     BigInteger x = xx.modPow(P_BI.add(BigInteger.valueOf(3L)).divide(BigInteger.valueOf(8L)), P_BI);
/*  60 */     if (!x.pow(2).subtract(xx).mod(P_BI).equals(BigInteger.ZERO)) {
/*  61 */       x = x.multiply(SQRTM1_BI).mod(P_BI);
/*     */     }
/*  63 */     if (x.testBit(0)) {
/*  64 */       x = P_BI.subtract(x);
/*     */     }
/*  66 */     return x;
/*     */   }
/*     */   
/*     */   private static Point edwards(Point a, Point b) {
/*  70 */     Point o = new Point();
/*  71 */     BigInteger xxyy = D_BI.multiply(a.x.multiply(b.x).multiply(a.y).multiply(b.y)).mod(P_BI);
/*  72 */     o.x = a
/*  73 */       .x.multiply(b.y).add(b.x.multiply(a.y))
/*  74 */       .multiply(BigInteger.ONE.add(xxyy).modInverse(P_BI))
/*  75 */       .mod(P_BI);
/*  76 */     o.y = a
/*  77 */       .y.multiply(b.y).add(a.x.multiply(b.x))
/*  78 */       .multiply(BigInteger.ONE.subtract(xxyy).modInverse(P_BI))
/*  79 */       .mod(P_BI);
/*  80 */     return o;
/*     */   }
/*     */   
/*     */   private static byte[] toLittleEndian(BigInteger n) {
/*  84 */     byte[] b = new byte[32];
/*  85 */     byte[] nBytes = n.toByteArray();
/*  86 */     System.arraycopy(nBytes, 0, b, 32 - nBytes.length, nBytes.length);
/*  87 */     for (int i = 0; i < b.length / 2; i++) {
/*  88 */       byte t = b[i];
/*  89 */       b[i] = b[b.length - i - 1];
/*  90 */       b[b.length - i - 1] = t;
/*     */     } 
/*  92 */     return b;
/*     */   }
/*     */   
/*     */   private static Ed25519.CachedXYT getCachedXYT(Point p) {
/*  96 */     return new Ed25519.CachedXYT(
/*  97 */         Field25519.expand(toLittleEndian(p.y.add(p.x).mod(P_BI))), 
/*  98 */         Field25519.expand(toLittleEndian(p.y.subtract(p.x).mod(P_BI))), 
/*  99 */         Field25519.expand(toLittleEndian(D2_BI.multiply(p.x).multiply(p.y).mod(P_BI))));
/*     */   }
/*     */   
/*     */   static {
/* 103 */     Point b = new Point();
/* 104 */     b.y = BigInteger.valueOf(4L).multiply(BigInteger.valueOf(5L).modInverse(P_BI)).mod(P_BI);
/* 105 */     b.x = recoverX(b.y);
/*     */     
/* 107 */     D = Field25519.expand(toLittleEndian(D_BI));
/* 108 */     D2 = Field25519.expand(toLittleEndian(D2_BI));
/* 109 */     SQRTM1 = Field25519.expand(toLittleEndian(SQRTM1_BI));
/*     */     
/* 111 */     Point bi = b;
/* 112 */     B_TABLE = new Ed25519.CachedXYT[32][8];
/* 113 */     for (int i = 0; i < 32; i++) {
/* 114 */       Point bij = bi; int k;
/* 115 */       for (k = 0; k < 8; k++) {
/* 116 */         B_TABLE[i][k] = getCachedXYT(bij);
/* 117 */         bij = edwards(bij, bi);
/*     */       } 
/* 119 */       for (k = 0; k < 8; k++) {
/* 120 */         bi = edwards(bi, bi);
/*     */       }
/*     */     } 
/* 123 */     bi = b;
/* 124 */     Point b2 = edwards(b, b);
/* 125 */     B2 = new Ed25519.CachedXYT[8];
/* 126 */     for (int j = 0; j < 8; j++) {
/* 127 */       B2[j] = getCachedXYT(bi);
/* 128 */       bi = edwards(bi, b2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\Ed25519Constants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */