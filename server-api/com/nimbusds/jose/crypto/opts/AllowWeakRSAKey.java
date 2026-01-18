/*    */ package com.nimbusds.jose.crypto.opts;
/*    */ 
/*    */ import com.nimbusds.jose.JWEDecrypterOption;
/*    */ import com.nimbusds.jose.JWSSignerOption;
/*    */ import com.nimbusds.jose.shaded.jcip.Immutable;
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
/*    */ @Immutable
/*    */ public final class AllowWeakRSAKey
/*    */   implements JWSSignerOption, JWEDecrypterOption
/*    */ {
/* 39 */   private static final AllowWeakRSAKey SINGLETON = new AllowWeakRSAKey();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AllowWeakRSAKey getInstance() {
/* 48 */     return SINGLETON;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "AllowWeakRSAKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\opts\AllowWeakRSAKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */