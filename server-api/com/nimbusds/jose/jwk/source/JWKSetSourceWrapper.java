/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class JWKSetSourceWrapper<C extends SecurityContext>
/*    */   implements JWKSetSource<C>
/*    */ {
/*    */   private final JWKSetSource<C> source;
/*    */   
/*    */   public JWKSetSourceWrapper(JWKSetSource<C> source) {
/* 50 */     Objects.requireNonNull(source);
/* 51 */     this.source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSetSource<C> getSource() {
/* 61 */     return this.source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 67 */     this.source.close();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSetSourceWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */