/*     */ package com.nimbusds.jose.util.health;
/*     */ 
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.events.Event;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class HealthReport<S, C extends SecurityContext>
/*     */   implements Event<S, C>
/*     */ {
/*     */   private final S source;
/*     */   private final HealthStatus status;
/*     */   private final Exception exception;
/*     */   private final long timestamp;
/*     */   private final C context;
/*     */   
/*     */   public HealthReport(S source, HealthStatus status, long timestamp, C context) {
/*  81 */     this(source, status, null, timestamp, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HealthReport(S source, HealthStatus status, Exception exception, long timestamp, C context) {
/* 102 */     Objects.requireNonNull(source);
/* 103 */     this.source = source;
/* 104 */     Objects.requireNonNull(status);
/* 105 */     this.status = status;
/* 106 */     if (exception != null && HealthStatus.HEALTHY.equals(status)) {
/* 107 */       throw new IllegalArgumentException("Exception not accepted for a healthy status");
/*     */     }
/* 109 */     this.exception = exception;
/* 110 */     this.timestamp = timestamp;
/* 111 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public S getSource() {
/* 117 */     return this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public C getContext() {
/* 123 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HealthStatus getHealthStatus() {
/* 133 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getException() {
/* 144 */     return this.exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimestamp() {
/* 154 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 160 */     StringBuilder sb = new StringBuilder("HealthReport{");
/* 161 */     sb.append("source=").append(this.source);
/* 162 */     sb.append(", status=").append(this.status);
/* 163 */     sb.append(", exception=").append(this.exception);
/* 164 */     sb.append(", timestamp=").append(this.timestamp);
/* 165 */     sb.append(", context=").append(this.context);
/* 166 */     sb.append('}');
/* 167 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\health\HealthReport.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */