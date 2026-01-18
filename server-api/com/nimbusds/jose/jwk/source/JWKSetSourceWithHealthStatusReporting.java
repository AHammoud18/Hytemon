/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.KeySourceException;
/*    */ import com.nimbusds.jose.jwk.JWKSet;
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*    */ import com.nimbusds.jose.util.health.HealthReport;
/*    */ import com.nimbusds.jose.util.health.HealthReportListener;
/*    */ import com.nimbusds.jose.util.health.HealthStatus;
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
/*    */ 
/*    */ 
/*    */ @ThreadSafe
/*    */ public class JWKSetSourceWithHealthStatusReporting<C extends SecurityContext>
/*    */   extends JWKSetSourceWrapper<C>
/*    */ {
/*    */   private final HealthReportListener<JWKSetSourceWithHealthStatusReporting<C>, C> healthReportListener;
/*    */   
/*    */   public JWKSetSourceWithHealthStatusReporting(JWKSetSource<C> source, HealthReportListener<JWKSetSourceWithHealthStatusReporting<C>, C> healthReportListener) {
/* 58 */     super(source);
/* 59 */     Objects.requireNonNull(healthReportListener);
/* 60 */     this.healthReportListener = healthReportListener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/*    */     JWKSet jwkSet;
/*    */     try {
/* 70 */       jwkSet = getSource().getJWKSet(refreshEvaluator, currentTime, context);
/* 71 */       this.healthReportListener.notify(new HealthReport(this, HealthStatus.HEALTHY, currentTime, (SecurityContext)context));
/* 72 */     } catch (Exception e) {
/* 73 */       this.healthReportListener.notify(new HealthReport(this, HealthStatus.NOT_HEALTHY, e, currentTime, (SecurityContext)context));
/* 74 */       throw e;
/*    */     } 
/*    */     
/* 77 */     return jwkSet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSetSourceWithHealthStatusReporting.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */