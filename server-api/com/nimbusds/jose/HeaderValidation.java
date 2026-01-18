/*    */ package com.nimbusds.jose;
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
/*    */ class HeaderValidation
/*    */ {
/*    */   static void ensureDisjoint(Header header, UnprotectedHeader unprotectedHeader) throws IllegalHeaderException {
/* 44 */     if (header == null || unprotectedHeader == null) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     for (String unprotectedParamName : unprotectedHeader.getIncludedParams()) {
/* 49 */       if (header.getIncludedParams().contains(unprotectedParamName))
/* 50 */         throw new IllegalHeaderException("The parameters in the protected header and the unprotected header must be disjoint"); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\HeaderValidation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */