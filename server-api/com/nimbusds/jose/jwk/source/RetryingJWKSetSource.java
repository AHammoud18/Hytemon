/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.KeySourceException;
/*    */ import com.nimbusds.jose.jwk.JWKSet;
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*    */ import com.nimbusds.jose.util.events.EventListener;
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
/*    */ @ThreadSafe
/*    */ public class RetryingJWKSetSource<C extends SecurityContext>
/*    */   extends JWKSetSourceWrapper<C>
/*    */ {
/*    */   private final EventListener<RetryingJWKSetSource<C>, C> eventListener;
/*    */   
/*    */   public static class RetrialEvent<C extends SecurityContext>
/*    */     extends AbstractJWKSetSourceEvent<RetryingJWKSetSource<C>, C>
/*    */   {
/*    */     private final Exception exception;
/*    */     
/*    */     private RetrialEvent(RetryingJWKSetSource<C> source, Exception exception, C securityContext) {
/* 52 */       super(source, securityContext);
/* 53 */       Objects.requireNonNull(exception);
/* 54 */       this.exception = exception;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Exception getException() {
/* 64 */       return this.exception;
/*    */     }
/*    */   }
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
/*    */   public RetryingJWKSetSource(JWKSetSource<C> source, EventListener<RetryingJWKSetSource<C>, C> eventListener) {
/* 82 */     super(source);
/* 83 */     this.eventListener = eventListener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/*    */     try {
/* 92 */       return getSource().getJWKSet(refreshEvaluator, currentTime, context);
/*    */     }
/* 94 */     catch (JWKSetUnavailableException e) {
/*    */       
/* 96 */       if (this.eventListener != null) {
/* 97 */         this.eventListener.notify(new RetrialEvent<>(this, (Exception)e, (SecurityContext)context));
/*    */       }
/* 99 */       return getSource().getJWKSet(refreshEvaluator, currentTime, context);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\RetryingJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */