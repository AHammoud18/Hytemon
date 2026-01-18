/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.SentryId;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ public final class W3CTraceparentHeader
/*    */ {
/*    */   public static final String TRACEPARENT_HEADER = "traceparent";
/*    */   @NotNull
/*    */   private final SentryId traceId;
/*    */   @NotNull
/*    */   private final SpanId spanId;
/*    */   @Nullable
/*    */   private final Boolean sampled;
/*    */   
/*    */   public W3CTraceparentHeader(@NotNull SentryId traceId, @NotNull SpanId spanId, @Nullable Boolean sampled) {
/* 19 */     this.traceId = traceId;
/* 20 */     this.spanId = spanId;
/* 21 */     this.sampled = sampled;
/*    */   }
/*    */   @NotNull
/*    */   public String getName() {
/* 25 */     return "traceparent";
/*    */   }
/*    */   @NotNull
/*    */   public String getValue() {
/* 29 */     String sampledFlag = (this.sampled != null && this.sampled.booleanValue()) ? "01" : "00";
/* 30 */     return String.format("00-%s-%s-%s", new Object[] { this.traceId, this.spanId, sampledFlag });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\W3CTraceparentHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */