/*    */ package io.sentry.logger;
/*    */ 
/*    */ import io.sentry.SentryAttributes;
/*    */ import io.sentry.SentryDate;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class SentryLogParameters {
/*    */   @Nullable
/*    */   private SentryDate timestamp;
/*    */   @NotNull
/* 12 */   private String origin = "manual"; @Nullable
/*    */   private SentryAttributes attributes; @Nullable
/*    */   public SentryDate getTimestamp() {
/* 15 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public void setTimestamp(@Nullable SentryDate timestamp) {
/* 19 */     this.timestamp = timestamp;
/*    */   }
/*    */   @Nullable
/*    */   public SentryAttributes getAttributes() {
/* 23 */     return this.attributes;
/*    */   }
/*    */   
/*    */   public void setAttributes(@Nullable SentryAttributes attributes) {
/* 27 */     this.attributes = attributes;
/*    */   }
/*    */   @NotNull
/*    */   public String getOrigin() {
/* 31 */     return this.origin;
/*    */   }
/*    */   
/*    */   public void setOrigin(@NotNull String origin) {
/* 35 */     this.origin = origin;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static SentryLogParameters create(@Nullable SentryDate timestamp, @Nullable SentryAttributes attributes) {
/* 40 */     SentryLogParameters params = new SentryLogParameters();
/*    */     
/* 42 */     params.setTimestamp(timestamp);
/* 43 */     params.setAttributes(attributes);
/*    */     
/* 45 */     return params;
/*    */   }
/*    */   @NotNull
/*    */   public static SentryLogParameters create(@Nullable SentryAttributes attributes) {
/* 49 */     return create(null, attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\SentryLogParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */