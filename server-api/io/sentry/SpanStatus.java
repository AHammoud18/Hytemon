/*     */ package io.sentry;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public enum SpanStatus
/*     */   implements JsonSerializable {
/*  10 */   OK(0, 399),
/*     */   
/*  12 */   CANCELLED(499),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  17 */   INTERNAL_ERROR(500),
/*     */   
/*  19 */   UNKNOWN(500),
/*     */   
/*  21 */   UNKNOWN_ERROR(500),
/*     */   
/*  23 */   INVALID_ARGUMENT(400),
/*     */   
/*  25 */   DEADLINE_EXCEEDED(504),
/*     */ 
/*     */   
/*  28 */   NOT_FOUND(404),
/*     */ 
/*     */   
/*  31 */   ALREADY_EXISTS(409),
/*     */ 
/*     */   
/*  34 */   PERMISSION_DENIED(403),
/*     */ 
/*     */   
/*  37 */   RESOURCE_EXHAUSTED(429),
/*     */ 
/*     */   
/*  40 */   FAILED_PRECONDITION(400),
/*     */ 
/*     */   
/*  43 */   ABORTED(409),
/*     */ 
/*     */   
/*  46 */   OUT_OF_RANGE(400),
/*     */ 
/*     */   
/*  49 */   UNIMPLEMENTED(501),
/*     */ 
/*     */   
/*  52 */   UNAVAILABLE(503),
/*     */ 
/*     */   
/*  55 */   DATA_LOSS(500),
/*     */ 
/*     */   
/*  58 */   UNAUTHENTICATED(401);
/*     */   
/*     */   private final int minHttpStatusCode;
/*     */   private final int maxHttpStatusCode;
/*     */   
/*     */   SpanStatus(int httpStatusCode) {
/*  64 */     this.minHttpStatusCode = httpStatusCode;
/*  65 */     this.maxHttpStatusCode = httpStatusCode;
/*     */   }
/*     */   
/*     */   SpanStatus(int minHttpStatusCode, int maxHttpStatusCode) {
/*  69 */     this.minHttpStatusCode = minHttpStatusCode;
/*  70 */     this.maxHttpStatusCode = maxHttpStatusCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static SpanStatus fromHttpStatusCode(int httpStatusCode) {
/*  80 */     for (SpanStatus status : values()) {
/*  81 */       if (status.matches(httpStatusCode)) {
/*  82 */         return status;
/*     */       }
/*     */     } 
/*  85 */     return null;
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
/*     */   @NotNull
/*     */   public static SpanStatus fromHttpStatusCode(@Nullable Integer httpStatusCode, @NotNull SpanStatus defaultStatus) {
/*  98 */     SpanStatus spanStatus = (httpStatusCode != null) ? fromHttpStatusCode(httpStatusCode.intValue()) : defaultStatus;
/*  99 */     return (spanStatus != null) ? spanStatus : defaultStatus;
/*     */   }
/*     */   
/*     */   private boolean matches(int httpStatusCode) {
/* 103 */     return (httpStatusCode >= this.minHttpStatusCode && httpStatusCode <= this.maxHttpStatusCode);
/*     */   }
/*     */   @NotNull
/*     */   public String apiName() {
/* 107 */     return name().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   @Nullable
/*     */   public static SpanStatus fromApiNameSafely(@Nullable String apiName) {
/* 111 */     if (apiName == null) {
/* 112 */       return null;
/*     */     }
/*     */     try {
/* 115 */       return valueOf(apiName.toUpperCase(Locale.ROOT));
/* 116 */     } catch (IllegalArgumentException ex) {
/* 117 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 126 */     writer.value(apiName());
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SpanStatus>
/*     */   {
/*     */     @NotNull
/*     */     public SpanStatus deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 134 */       return SpanStatus.valueOf(reader.nextString().toUpperCase(Locale.ROOT));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SpanStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */