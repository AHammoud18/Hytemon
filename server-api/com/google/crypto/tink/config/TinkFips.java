/*    */ package com.google.crypto.tink.config;
/*    */ 
/*    */ import com.google.crypto.tink.Registry;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ public final class TinkFips
/*    */ {
/*    */   public static boolean useOnlyFips() {
/* 30 */     return TinkFipsUtil.useOnlyFips();
/*    */   }
/*    */   
/*    */   public static void restrictToFips() throws GeneralSecurityException {
/* 34 */     Registry.restrictToFipsIfEmpty();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\config\TinkFips.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */