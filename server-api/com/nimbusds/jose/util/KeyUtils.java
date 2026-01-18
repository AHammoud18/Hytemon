/*    */ package com.nimbusds.jose.util;
/*    */ 
/*    */ import javax.crypto.SecretKey;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyUtils
/*    */ {
/*    */   public static SecretKey toAESKey(final SecretKey secretKey) {
/* 44 */     if (secretKey == null || secretKey.getAlgorithm().equals("AES")) {
/* 45 */       return secretKey;
/*    */     }
/*    */     
/* 48 */     return new SecretKey()
/*    */       {
/*    */         public String getAlgorithm() {
/* 51 */           return "AES";
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public String getFormat() {
/* 57 */           return secretKey.getFormat();
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public byte[] getEncoded() {
/* 63 */           return secretKey.getEncoded();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\KeyUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */