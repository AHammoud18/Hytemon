/*    */ package com.google.crypto.tink.mac.internal;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AesUtil
/*    */ {
/*    */   public static final int BLOCK_SIZE = 16;
/*    */   
/*    */   public static byte[] dbl(byte[] value) {
/* 40 */     if (value.length != 16) {
/* 41 */       throw new IllegalArgumentException("value must be a block.");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     byte[] res = new byte[16];
/* 51 */     for (int i = 0; i < 16; i++) {
/* 52 */       res[i] = (byte)(0xFE & value[i] << 1);
/* 53 */       if (i < 15) {
/* 54 */         res[i] = (byte)(res[i] | (byte)(0x1 & value[i + 1] >> 7));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 59 */     res[15] = (byte)(res[15] ^ (byte)(0x87 & value[0] >> 7));
/* 60 */     return res;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] cmacPad(byte[] x) {
/* 72 */     if (x.length >= 16) {
/* 73 */       throw new IllegalArgumentException("x must be smaller than a block.");
/*    */     }
/* 75 */     byte[] result = Arrays.copyOf(x, 16);
/* 76 */     result[x.length] = Byte.MIN_VALUE;
/* 77 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\AesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */