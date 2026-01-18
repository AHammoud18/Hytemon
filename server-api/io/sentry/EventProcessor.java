/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.SentryTransaction;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface EventProcessor
/*    */ {
/*    */   @Nullable
/*    */   default SentryEvent process(@NotNull SentryEvent event, @NotNull Hint hint) {
/* 21 */     return event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default SentryTransaction process(@NotNull SentryTransaction transaction, @NotNull Hint hint) {
/* 33 */     return transaction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default SentryReplayEvent process(@NotNull SentryReplayEvent event, @NotNull Hint hint) {
/* 45 */     return event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default SentryLogEvent process(@NotNull SentryLogEvent event) {
/* 56 */     return event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default Long getOrder() {
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\EventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */