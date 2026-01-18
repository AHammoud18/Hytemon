/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.util.SecretBytes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MlDsaMarshalUtil
/*     */ {
/*     */   static void simpleBitPack10(MlDsaArithmeticUtil.PolyRq w, byte[] z, int offset) throws GeneralSecurityException {
/*  34 */     if (offset + 320 > z.length) {
/*  35 */       throw new GeneralSecurityException("Provided buffer too short");
/*     */     }
/*     */     
/*  38 */     for (int i = 0; i < 64; i++) {
/*  39 */       int a = (w.polynomial[4 * i]).r;
/*  40 */       int b = (w.polynomial[4 * i + 1]).r;
/*  41 */       int c = (w.polynomial[4 * i + 2]).r;
/*  42 */       int d = (w.polynomial[4 * i + 3]).r;
/*     */ 
/*     */ 
/*     */       
/*  46 */       if (a >= 1024 || b >= 1024 || c >= 1024 || d >= 1024) {
/*  47 */         throw new GeneralSecurityException("Polynomial coefficient too large");
/*     */       }
/*  49 */       z[offset + 5 * i] = (byte)a;
/*  50 */       z[offset + 5 * i + 1] = (byte)(a >> 8 | b << 2);
/*  51 */       z[offset + 5 * i + 2] = (byte)(b >> 6 | c << 4);
/*  52 */       z[offset + 5 * i + 3] = (byte)(c >> 4 | d << 6);
/*  53 */       z[offset + 5 * i + 4] = (byte)(d >> 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void bitPack3(MlDsaArithmeticUtil.PolyRq w, byte[] z, int offset) throws GeneralSecurityException {
/*  59 */     if (offset + 96 > z.length) {
/*  60 */       throw new GeneralSecurityException("Provided buffer too short");
/*     */     }
/*  62 */     MlDsaArithmeticUtil.RingZq two = new MlDsaArithmeticUtil.RingZq(2);
/*     */     
/*  64 */     for (int i = 0; i < 32; i++) {
/*  65 */       int a = (two.minus(w.polynomial[8 * i])).r;
/*  66 */       int b = (two.minus(w.polynomial[8 * i + 1])).r;
/*  67 */       int c = (two.minus(w.polynomial[8 * i + 2])).r;
/*  68 */       int d = (two.minus(w.polynomial[8 * i + 3])).r;
/*  69 */       int e = (two.minus(w.polynomial[8 * i + 4])).r;
/*  70 */       int f = (two.minus(w.polynomial[8 * i + 5])).r;
/*  71 */       int g = (two.minus(w.polynomial[8 * i + 6])).r;
/*  72 */       int h = (two.minus(w.polynomial[8 * i + 7])).r;
/*  73 */       if (a > 4 || b > 4 || c > 4 || d > 4 || e > 4 || f > 4 || g > 4 || h > 4) {
/*  74 */         throw new GeneralSecurityException("Polynomial coefficients out of bounds");
/*     */       }
/*  76 */       z[offset + 3 * i] = (byte)(a | b << 3 | c << 6);
/*  77 */       z[offset + 3 * i + 1] = (byte)(c >> 2 | d << 1 | e << 4 | f << 7);
/*  78 */       z[offset + 3 * i + 2] = (byte)(f >> 1 | g << 2 | h << 5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void bitPack4(MlDsaArithmeticUtil.PolyRq w, byte[] z, int offset) throws GeneralSecurityException {
/*  84 */     if (offset + 128 > z.length) {
/*  85 */       throw new GeneralSecurityException("Provided buffer too short");
/*     */     }
/*  87 */     MlDsaArithmeticUtil.RingZq four = new MlDsaArithmeticUtil.RingZq(4);
/*     */     
/*  89 */     for (int i = 0; i < 128; i++) {
/*  90 */       int a = (four.minus(w.polynomial[2 * i])).r;
/*  91 */       int b = (four.minus(w.polynomial[2 * i + 1])).r;
/*  92 */       if (a > 8 || b > 8) {
/*  93 */         throw new GeneralSecurityException("Polynomial coefficients out of bounds");
/*     */       }
/*  95 */       z[offset + i] = (byte)(a | b << 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void bitPack13(MlDsaArithmeticUtil.PolyRq w, byte[] z, int offset) throws GeneralSecurityException {
/* 101 */     if (offset + 416 > z.length) {
/* 102 */       throw new GeneralSecurityException("Provided buffer too short");
/*     */     }
/* 104 */     MlDsaArithmeticUtil.RingZq twoPowDMinusOne = new MlDsaArithmeticUtil.RingZq(4096);
/*     */     
/* 106 */     for (int i = 0; i < 32; i++) {
/* 107 */       int a = (twoPowDMinusOne.minus(w.polynomial[8 * i])).r;
/* 108 */       int b = (twoPowDMinusOne.minus(w.polynomial[8 * i + 1])).r;
/* 109 */       int c = (twoPowDMinusOne.minus(w.polynomial[8 * i + 2])).r;
/* 110 */       int d = (twoPowDMinusOne.minus(w.polynomial[8 * i + 3])).r;
/* 111 */       int e = (twoPowDMinusOne.minus(w.polynomial[8 * i + 4])).r;
/* 112 */       int f = (twoPowDMinusOne.minus(w.polynomial[8 * i + 5])).r;
/* 113 */       int g = (twoPowDMinusOne.minus(w.polynomial[8 * i + 6])).r;
/* 114 */       int h = (twoPowDMinusOne.minus(w.polynomial[8 * i + 7])).r;
/* 115 */       if (a >= 8192 || b >= 8192 || c >= 8192 || d >= 8192 || e >= 8192 || f >= 8192 || g >= 8192 || h >= 8192)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 123 */         throw new GeneralSecurityException("Polynomial coefficient too large");
/*     */       }
/* 125 */       z[offset + 13 * i] = (byte)a;
/* 126 */       z[offset + 13 * i + 1] = (byte)(a >> 8 | b << 5);
/* 127 */       z[offset + 13 * i + 2] = (byte)(b >> 3);
/* 128 */       z[offset + 13 * i + 3] = (byte)(b >> 11 | c << 2);
/* 129 */       z[offset + 13 * i + 4] = (byte)(c >> 6 | d << 7);
/* 130 */       z[offset + 13 * i + 5] = (byte)(d >> 1);
/* 131 */       z[offset + 13 * i + 6] = (byte)(d >> 9 | e << 4);
/* 132 */       z[offset + 13 * i + 7] = (byte)(e >> 4);
/* 133 */       z[offset + 13 * i + 8] = (byte)(e >> 12 | f << 1);
/* 134 */       z[offset + 13 * i + 9] = (byte)(f >> 7 | g << 6);
/* 135 */       z[offset + 13 * i + 10] = (byte)(g >> 2);
/* 136 */       z[offset + 13 * i + 11] = (byte)(g >> 10 | h << 3);
/* 137 */       z[offset + 13 * i + 12] = (byte)(h >> 5);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] pkEncode(byte[] rho, MlDsaArithmeticUtil.VectorRq t1Bold, MlDsaConstants.Params params) throws GeneralSecurityException {
/* 145 */     if (rho.length != 32 || t1Bold.vector.length != params.k) {
/* 146 */       throw new GeneralSecurityException("Invalid parameters length for pkEncode");
/*     */     }
/* 148 */     byte[] pk = new byte[params.pkLength];
/* 149 */     System.arraycopy(rho, 0, pk, 0, 32);
/* 150 */     for (int i = 0; i < params.k; i++) {
/* 151 */       simpleBitPack10(t1Bold.vector[i], pk, 32 + 32 * i * 10);
/*     */     }
/*     */     
/* 154 */     return pk;
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
/*     */   static SecretBytes skEncode(byte[] rho, byte[] capK, byte[] tr, MlDsaArithmeticUtil.VectorRq s1Bold, MlDsaArithmeticUtil.VectorRq s2Bold, MlDsaArithmeticUtil.VectorRq t0Bold, MlDsaConstants.Params params) throws GeneralSecurityException {
/* 167 */     if (rho.length != 32 || capK.length != 32 || tr.length != 64 || s1Bold.vector.length != params.l || s2Bold.vector.length != params.k || t0Bold.vector.length != params.k)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       throw new GeneralSecurityException("Invalid parameters length");
/*     */     }
/*     */     
/* 176 */     byte[] sk = new byte[params.skLength];
/* 177 */     System.arraycopy(rho, 0, sk, 0, 32);
/* 178 */     System.arraycopy(capK, 0, sk, 32, 32);
/* 179 */     System.arraycopy(tr, 0, sk, 64, 64);
/*     */     
/* 181 */     int baseOffset = 128;
/* 182 */     if (params.eta == 2) {
/* 183 */       int j; for (j = 0; j < params.l; j++) {
/* 184 */         bitPack3(s1Bold.vector[j], sk, baseOffset + 32 * j * params.bitlen2Eta);
/*     */       }
/* 186 */       baseOffset += 32 * params.l * params.bitlen2Eta;
/* 187 */       for (j = 0; j < params.k; j++) {
/* 188 */         bitPack3(s2Bold.vector[j], sk, baseOffset + 32 * j * params.bitlen2Eta);
/*     */       }
/* 190 */     } else if (params.eta == 4) {
/* 191 */       int j; for (j = 0; j < params.l; j++) {
/* 192 */         bitPack4(s1Bold.vector[j], sk, baseOffset + 32 * j * params.bitlen2Eta);
/*     */       }
/* 194 */       baseOffset += 32 * params.l * params.bitlen2Eta;
/* 195 */       for (j = 0; j < params.k; j++) {
/* 196 */         bitPack4(s2Bold.vector[j], sk, baseOffset + 32 * j * params.bitlen2Eta);
/*     */       }
/*     */     } 
/*     */     
/* 200 */     baseOffset += 32 * params.k * params.bitlen2Eta;
/* 201 */     for (int i = 0; i < params.k; i++) {
/* 202 */       bitPack13(t0Bold.vector[i], sk, baseOffset + 32 * i * 13);
/*     */     }
/*     */     
/* 205 */     return SecretBytes.copyFrom(sk, InsecureSecretKeyAccess.get());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\MlDsaMarshalUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */