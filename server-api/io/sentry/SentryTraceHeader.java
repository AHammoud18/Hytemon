/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.exception.InvalidSentryTraceHeaderException;
/*    */ import io.sentry.protocol.SentryId;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ public final class SentryTraceHeader
/*    */ {
/*    */   public static final String SENTRY_TRACE_HEADER = "sentry-trace";
/*    */   @NotNull
/*    */   private final SentryId traceId;
/*    */   @NotNull
/*    */   private final SpanId spanId;
/*    */   @Nullable
/*    */   private final Boolean sampled;
/* 20 */   private static final Pattern SENTRY_TRACEPARENT_HEADER_REGEX = Pattern.compile("^[ \\t]*([0-9a-f]{32})-([0-9a-f]{16})(-[01])?[ \\t]*$", 2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SentryTraceHeader(@NotNull SentryId traceId, @NotNull SpanId spanId, @Nullable Boolean sampled) {
/* 27 */     this.traceId = traceId;
/* 28 */     this.spanId = spanId;
/* 29 */     this.sampled = sampled;
/*    */   }
/*    */   
/*    */   public SentryTraceHeader(@NotNull String value) throws InvalidSentryTraceHeaderException {
/* 33 */     Matcher matcher = SENTRY_TRACEPARENT_HEADER_REGEX.matcher(value);
/* 34 */     boolean matchesExist = matcher.matches();
/*    */     
/* 36 */     if (!matchesExist) {
/* 37 */       throw new InvalidSentryTraceHeaderException(value);
/*    */     }
/*    */     
/* 40 */     this.traceId = new SentryId(matcher.group(1));
/* 41 */     this.spanId = new SpanId(matcher.group(2));
/*    */     
/* 43 */     String sampled = matcher.group(3);
/* 44 */     this.sampled = (sampled == null) ? null : Boolean.valueOf("1".equals(sampled.substring(1)));
/*    */   }
/*    */   @NotNull
/*    */   public String getName() {
/* 48 */     return "sentry-trace";
/*    */   }
/*    */   @NotNull
/*    */   public String getValue() {
/* 52 */     if (this.sampled != null) {
/* 53 */       return String.format("%s-%s-%s", new Object[] { this.traceId, this.spanId, this.sampled.booleanValue() ? "1" : "0" });
/*    */     }
/* 55 */     return String.format("%s-%s", new Object[] { this.traceId, this.spanId });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public SentryId getTraceId() {
/* 60 */     return this.traceId;
/*    */   }
/*    */   @NotNull
/*    */   public SpanId getSpanId() {
/* 64 */     return this.spanId;
/*    */   }
/*    */   @Nullable
/*    */   public Boolean isSampled() {
/* 68 */     return this.sampled;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryTraceHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */