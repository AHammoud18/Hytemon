/*    */ package io.sentry.clientreport;
/*    */ 
/*    */ public enum DiscardReason {
/*  4 */   QUEUE_OVERFLOW("queue_overflow"),
/*  5 */   CACHE_OVERFLOW("cache_overflow"),
/*  6 */   RATELIMIT_BACKOFF("ratelimit_backoff"),
/*  7 */   NETWORK_ERROR("network_error"),
/*  8 */   SAMPLE_RATE("sample_rate"),
/*  9 */   BEFORE_SEND("before_send"),
/* 10 */   EVENT_PROCESSOR("event_processor"),
/* 11 */   BACKPRESSURE("backpressure");
/*    */   
/*    */   private final String reason;
/*    */   
/*    */   DiscardReason(String reason) {
/* 16 */     this.reason = reason;
/*    */   }
/*    */   
/*    */   public String getReason() {
/* 20 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\DiscardReason.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */