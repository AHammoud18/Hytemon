/*    */ package io.sentry.protocol;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.JsonDeserializer;
/*    */ import io.sentry.JsonSerializable;
/*    */ import io.sentry.ObjectReader;
/*    */ import io.sentry.ObjectWriter;
/*    */ import io.sentry.SentryUUID;
/*    */ import io.sentry.util.LazyEvaluator;
/*    */ import io.sentry.util.StringUtils;
/*    */ import io.sentry.util.UUIDStringUtils;
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class SentryId
/*    */   implements JsonSerializable {
/* 19 */   public static final SentryId EMPTY_ID = new SentryId("00000000-0000-0000-0000-000000000000"
/* 20 */       .replace("-", ""));
/*    */   @NotNull
/*    */   private final LazyEvaluator<String> lazyStringValue;
/*    */   
/*    */   public SentryId() {
/* 25 */     this((UUID)null);
/*    */   }
/*    */   
/*    */   public SentryId(@Nullable UUID uuid) {
/* 29 */     if (uuid != null) {
/* 30 */       this.lazyStringValue = new LazyEvaluator(() -> normalize(UUIDStringUtils.toSentryIdString(uuid)));
/*    */     } else {
/*    */       
/* 33 */       this.lazyStringValue = new LazyEvaluator(SentryUUID::generateSentryId);
/*    */     } 
/*    */   }
/*    */   
/*    */   public SentryId(@NotNull String sentryIdString) {
/* 38 */     String normalized = StringUtils.normalizeUUID(sentryIdString);
/* 39 */     if (normalized.length() != 32 && normalized.length() != 36) {
/* 40 */       throw new IllegalArgumentException("String representation of SentryId has either 32 (UUID no dashes) or 36 characters long (completed UUID). Received: " + sentryIdString);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 45 */     if (normalized.length() == 36) {
/* 46 */       this.lazyStringValue = new LazyEvaluator(() -> normalize(normalized));
/*    */     } else {
/* 48 */       this.lazyStringValue = new LazyEvaluator(() -> normalized);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return (String)this.lazyStringValue.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 59 */     if (this == o) return true; 
/* 60 */     if (o == null || getClass() != o.getClass()) return false; 
/* 61 */     SentryId sentryId = (SentryId)o;
/* 62 */     return ((String)this.lazyStringValue.getValue()).equals(sentryId.lazyStringValue.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 67 */     return ((String)this.lazyStringValue.getValue()).hashCode();
/*    */   }
/*    */   @NotNull
/*    */   private String normalize(@NotNull String uuidString) {
/* 71 */     return StringUtils.normalizeUUID(uuidString).replace("-", "");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 79 */     writer.value(toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<SentryId>
/*    */   {
/*    */     @NotNull
/*    */     public SentryId deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 88 */       return new SentryId(reader.nextString());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */