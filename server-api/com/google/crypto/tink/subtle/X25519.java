/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.annotations.Alpha;
/*     */ import com.google.crypto.tink.internal.Curve25519;
/*     */ import com.google.crypto.tink.internal.Field25519;
/*     */ import java.security.InvalidKeyException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Alpha
/*     */ public final class X25519
/*     */ {
/*     */   public static byte[] generatePrivateKey() {
/*  74 */     byte[] privateKey = Random.randBytes(32);
/*     */     
/*  76 */     privateKey[0] = (byte)(privateKey[0] | 0x7);
/*  77 */     privateKey[31] = (byte)(privateKey[31] & 0x3F);
/*  78 */     privateKey[31] = (byte)(privateKey[31] | 0x80);
/*     */     
/*  80 */     return privateKey;
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
/*     */   public static byte[] computeSharedSecret(byte[] privateKey, byte[] peersPublicValue) throws InvalidKeyException {
/*  95 */     if (privateKey.length != 32) {
/*  96 */       throw new InvalidKeyException("Private key must have 32 bytes.");
/*     */     }
/*  98 */     long[] x = new long[11];
/*     */     
/* 100 */     byte[] e = Arrays.copyOf(privateKey, 32);
/* 101 */     e[0] = (byte)(e[0] & 0xF8);
/* 102 */     e[31] = (byte)(e[31] & Byte.MAX_VALUE);
/* 103 */     e[31] = (byte)(e[31] | 0x40);
/*     */     
/* 105 */     Curve25519.curveMult(x, e, peersPublicValue);
/* 106 */     return Field25519.contract(x);
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
/*     */   public static byte[] publicFromPrivate(byte[] privateKey) throws InvalidKeyException {
/* 118 */     if (privateKey.length != 32) {
/* 119 */       throw new InvalidKeyException("Private key must have 32 bytes.");
/*     */     }
/* 121 */     byte[] base = new byte[32];
/* 122 */     base[0] = 9;
/* 123 */     return computeSharedSecret(privateKey, base);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\X25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */