/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public enum SentryItemType
/*    */   implements JsonSerializable
/*    */ {
/* 12 */   Session("session"),
/* 13 */   Event("event"),
/* 14 */   UserFeedback("user_report"),
/* 15 */   Attachment("attachment"),
/* 16 */   Transaction("transaction"),
/* 17 */   Profile("profile"),
/* 18 */   ProfileChunk("profile_chunk"),
/* 19 */   ClientReport("client_report"),
/* 20 */   ReplayEvent("replay_event"),
/* 21 */   ReplayRecording("replay_recording"),
/* 22 */   ReplayVideo("replay_video"),
/* 23 */   CheckIn("check_in"),
/* 24 */   Feedback("feedback"),
/* 25 */   Log("log"),
/* 26 */   TraceMetric("trace_metric"),
/* 27 */   Span("span"),
/* 28 */   Unknown("__unknown__");
/*    */   
/*    */   private final String itemType;
/*    */   
/*    */   public static SentryItemType resolve(Object item) {
/* 33 */     if (item instanceof SentryEvent)
/* 34 */       return (((SentryEvent)item).getContexts().getFeedback() == null) ? Event : Feedback; 
/* 35 */     if (item instanceof io.sentry.protocol.SentryTransaction)
/* 36 */       return Transaction; 
/* 37 */     if (item instanceof Session)
/* 38 */       return Session; 
/* 39 */     if (item instanceof io.sentry.clientreport.ClientReport) {
/* 40 */       return ClientReport;
/*    */     }
/* 42 */     return Attachment;
/*    */   }
/*    */ 
/*    */   
/*    */   SentryItemType(String itemType) {
/* 47 */     this.itemType = itemType;
/*    */   }
/*    */   
/*    */   public String getItemType() {
/* 51 */     return this.itemType;
/*    */   }
/*    */   @NotNull
/*    */   public static SentryItemType valueOfLabel(String itemType) {
/* 55 */     for (SentryItemType sentryItemType : values()) {
/* 56 */       if (sentryItemType.itemType.equals(itemType)) {
/* 57 */         return sentryItemType;
/*    */       }
/*    */     } 
/* 60 */     return Unknown;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 66 */     writer.value(this.itemType);
/*    */   }
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<SentryItemType>
/*    */   {
/*    */     @NotNull
/*    */     public SentryItemType deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 74 */       return SentryItemType.valueOfLabel(reader.nextString().toLowerCase(Locale.ROOT));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */