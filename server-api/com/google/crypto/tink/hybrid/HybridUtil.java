/*    */ package com.google.crypto.tink.hybrid;
/*    */ 
/*    */ import com.google.crypto.tink.proto.HashType;
/*    */ import java.security.NoSuchAlgorithmException;
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
/*    */ final class HybridUtil
/*    */ {
/*    */   public static String toHmacAlgo(HashType hash) throws NoSuchAlgorithmException {
/* 30 */     switch (hash) {
/*    */       case SHA1:
/* 32 */         return "HmacSha1";
/*    */       case SHA224:
/* 34 */         return "HmacSha224";
/*    */       case SHA256:
/* 36 */         return "HmacSha256";
/*    */       case SHA384:
/* 38 */         return "HmacSha384";
/*    */       case SHA512:
/* 40 */         return "HmacSha512";
/*    */     } 
/* 42 */     throw new NoSuchAlgorithmException("hash unsupported for HMAC: " + hash);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */