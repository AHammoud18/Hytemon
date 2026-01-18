/*    */ package com.google.crypto.tink.tinkkey;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ @Immutable
/*    */ public final class KeyAccess
/*    */ {
/*    */   private final boolean canAccessSecret;
/*    */   
/*    */   private KeyAccess(boolean canAccessSecret) {
/* 35 */     this.canAccessSecret = canAccessSecret;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeyAccess publicAccess() {
/* 42 */     return new KeyAccess(false);
/*    */   }
/*    */   
/*    */   static KeyAccess secretAccess() {
/* 46 */     return new KeyAccess(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canAccessSecret() {
/* 53 */     return this.canAccessSecret;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\tinkkey\KeyAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */