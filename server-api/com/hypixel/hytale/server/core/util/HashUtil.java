/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bouncycastle.util.encoders.Hex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HashUtil
/*    */ {
/*    */   @Nonnull
/*    */   public static String sha256(byte[] bytes) {
/*    */     try {
/* 18 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
/* 19 */       messageDigest.update(bytes);
/* 20 */       return Hex.toHexString(messageDigest.digest());
/* 21 */     } catch (NoSuchAlgorithmException e) {
/* 22 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\HashUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */