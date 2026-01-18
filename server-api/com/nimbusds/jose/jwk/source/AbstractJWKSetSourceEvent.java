/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import com.nimbusds.jose.util.events.Event;
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
/*    */ class AbstractJWKSetSourceEvent<S extends JWKSetSource<C>, C extends SecurityContext>
/*    */   implements Event<S, C>
/*    */ {
/*    */   private final S source;
/*    */   private final C context;
/*    */   
/*    */   AbstractJWKSetSourceEvent(S source, C context) {
/* 48 */     Objects.requireNonNull(source);
/* 49 */     this.source = source;
/* 50 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public S getSource() {
/* 56 */     return this.source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public C getContext() {
/* 62 */     return this.context;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\AbstractJWKSetSourceEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */