/*    */ package com.nimbusds.jose.crypto.opts;
/*    */ 
/*    */ import com.nimbusds.jose.Option;
/*    */ import com.nimbusds.jose.crypto.impl.RSAKeyUtils;
/*    */ import java.security.PrivateKey;
/*    */ import java.util.Set;
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
/*    */ public class OptionUtils
/*    */ {
/*    */   @Deprecated
/*    */   public static <T extends Option> boolean optionIsPresent(Set<? extends Option> opts, Class<T> tClass) {
/* 52 */     if (opts == null || opts.isEmpty()) {
/* 53 */       return false;
/*    */     }
/*    */     
/* 56 */     for (Option o : opts) {
/* 57 */       if (o.getClass().isAssignableFrom(tClass)) {
/* 58 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 62 */     return false;
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
/*    */   
/*    */   public static void ensureMinRSAPrivateKeySize(PrivateKey privateKey, Set<? extends Option> opts) {
/* 75 */     if (!opts.contains(AllowWeakRSAKey.getInstance())) {
/*    */       
/* 77 */       int keyBitLength = RSAKeyUtils.keyBitLength(privateKey);
/*    */       
/* 79 */       if (keyBitLength > 0 && keyBitLength < 2048)
/* 80 */         throw new IllegalArgumentException("The RSA key size must be at least 2048 bits"); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\opts\OptionUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */