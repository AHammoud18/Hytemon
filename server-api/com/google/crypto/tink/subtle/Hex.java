/*    */ package com.google.crypto.tink.subtle;
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
/*    */ public final class Hex
/*    */ {
/*    */   public static String encode(byte[] bytes) {
/* 27 */     String chars = "0123456789abcdef";
/* 28 */     StringBuilder result = new StringBuilder(2 * bytes.length);
/* 29 */     for (byte b : bytes) {
/*    */       
/* 31 */       int val = b & 0xFF;
/* 32 */       result.append(chars.charAt(val / 16));
/* 33 */       result.append(chars.charAt(val % 16));
/*    */     } 
/* 35 */     return result.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] decode(String hex) {
/* 40 */     if (hex.length() % 2 != 0) {
/* 41 */       throw new IllegalArgumentException("Expected a string of even length");
/*    */     }
/* 43 */     int size = hex.length() / 2;
/* 44 */     byte[] result = new byte[size];
/* 45 */     for (int i = 0; i < size; i++) {
/* 46 */       int hi = Character.digit(hex.charAt(2 * i), 16);
/* 47 */       int lo = Character.digit(hex.charAt(2 * i + 1), 16);
/* 48 */       if (hi == -1 || lo == -1) {
/* 49 */         throw new IllegalArgumentException("input is not hexadecimal");
/*    */       }
/* 51 */       result[i] = (byte)(16 * hi + lo);
/*    */     } 
/* 53 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Hex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */