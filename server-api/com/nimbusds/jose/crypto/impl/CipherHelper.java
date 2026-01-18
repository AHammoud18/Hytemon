/*    */ package com.nimbusds.jose.crypto.impl;
/*    */ 
/*    */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.Provider;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.NoSuchPaddingException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ThreadSafe
/*    */ public class CipherHelper
/*    */ {
/*    */   public static Cipher getInstance(String name, Provider provider) throws NoSuchAlgorithmException, NoSuchPaddingException {
/* 53 */     if (provider == null) {
/* 54 */       return Cipher.getInstance(name);
/*    */     }
/* 56 */     return Cipher.getInstance(name, provider);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\CipherHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */