/*    */ package io.netty.handler.ssl;
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
/*    */ final class GroupsConverter
/*    */ {
/*    */   static String toOpenSsl(String key) {
/* 25 */     switch (key) {
/*    */       case "secp224r1":
/* 27 */         return "P-224";
/*    */       case "prime256v1":
/*    */       case "secp256r1":
/* 30 */         return "P-256";
/*    */       case "secp384r1":
/* 32 */         return "P-384";
/*    */       case "secp521r1":
/* 34 */         return "P-521";
/*    */       case "x25519":
/* 36 */         return "X25519";
/*    */     } 
/* 38 */     return key;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\GroupsConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */