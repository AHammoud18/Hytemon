/*    */ package com.nimbusds.jose.shaded.gson.internal;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NumberLimits
/*    */ {
/*    */   private static final int MAX_NUMBER_STRING_LENGTH = 10000;
/*    */   
/*    */   private static void checkNumberStringLength(String s) {
/* 16 */     if (s.length() > 10000) {
/* 17 */       throw new NumberFormatException("Number string too large: " + s.substring(0, 30) + "...");
/*    */     }
/*    */   }
/*    */   
/*    */   public static BigDecimal parseBigDecimal(String s) throws NumberFormatException {
/* 22 */     checkNumberStringLength(s);
/* 23 */     BigDecimal decimal = new BigDecimal(s);
/*    */ 
/*    */     
/* 26 */     if (Math.abs(decimal.scale()) >= 10000L) {
/* 27 */       throw new NumberFormatException("Number has unsupported scale: " + s);
/*    */     }
/* 29 */     return decimal;
/*    */   }
/*    */   
/*    */   public static BigInteger parseBigInteger(String s) throws NumberFormatException {
/* 33 */     checkNumberStringLength(s);
/* 34 */     return new BigInteger(s);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\NumberLimits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */