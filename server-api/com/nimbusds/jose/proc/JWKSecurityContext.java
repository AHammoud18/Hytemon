/*    */ package com.nimbusds.jose.proc;
/*    */ 
/*    */ import com.nimbusds.jose.jwk.JWK;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
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
/*    */ public class JWKSecurityContext
/*    */   implements SecurityContext
/*    */ {
/*    */   private final List<JWK> keys;
/*    */   
/*    */   public JWKSecurityContext(List<JWK> keys) {
/* 46 */     this.keys = Objects.<List<JWK>>requireNonNull(keys);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<JWK> getKeys() {
/* 55 */     return this.keys;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\JWKSecurityContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */