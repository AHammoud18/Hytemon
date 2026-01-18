/*    */ package io.sentry;
/*    */ 
/*    */ 
/*    */ public enum DataCategory
/*    */ {
/*  6 */   All("__all__"),
/*  7 */   Default("default"),
/*  8 */   Error("error"),
/*  9 */   Feedback("feedback"),
/* 10 */   Session("session"),
/* 11 */   Attachment("attachment"),
/* 12 */   LogItem("log_item"),
/* 13 */   LogByte("log_byte"),
/* 14 */   TraceMetric("trace_metric"),
/* 15 */   Monitor("monitor"),
/* 16 */   Profile("profile"),
/* 17 */   ProfileChunkUi("profile_chunk_ui"),
/* 18 */   ProfileChunk("profile_chunk"),
/* 19 */   Transaction("transaction"),
/* 20 */   Replay("replay"),
/* 21 */   Span("span"),
/* 22 */   Security("security"),
/* 23 */   UserReport("user_report"),
/* 24 */   Unknown("unknown");
/*    */   
/*    */   private final String category;
/*    */   
/*    */   DataCategory(String category) {
/* 29 */     this.category = category;
/*    */   }
/*    */   
/*    */   public String getCategory() {
/* 33 */     return this.category;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DataCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */