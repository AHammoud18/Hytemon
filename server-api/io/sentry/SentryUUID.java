/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.UUIDGenerator;
/*    */ import io.sentry.util.UUIDStringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SentryUUID
/*    */ {
/*    */   public static String generateSentryId() {
/* 17 */     return UUIDStringUtils.toSentryIdString(UUIDGenerator.randomUUID());
/*    */   }
/*    */   
/*    */   public static String generateSpanId() {
/* 21 */     return UUIDStringUtils.toSentrySpanIdString(UUIDGenerator.randomHalfLengthUUID());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryUUID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */