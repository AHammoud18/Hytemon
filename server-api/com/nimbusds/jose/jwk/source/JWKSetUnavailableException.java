/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.KeySourceException;
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
/*    */ public class JWKSetUnavailableException
/*    */   extends KeySourceException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public JWKSetUnavailableException(String message) {
/* 41 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSetUnavailableException(String message, Throwable cause) {
/* 52 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSetUnavailableException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */