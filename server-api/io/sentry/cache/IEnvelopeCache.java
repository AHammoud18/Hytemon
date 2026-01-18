/*    */ package io.sentry.cache;
/*    */ 
/*    */ import io.sentry.Hint;
/*    */ import io.sentry.SentryEnvelope;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public interface IEnvelopeCache
/*    */   extends Iterable<SentryEnvelope> {
/*    */   @Deprecated
/*    */   void store(@NotNull SentryEnvelope paramSentryEnvelope, @NotNull Hint paramHint);
/*    */   
/*    */   default boolean storeEnvelope(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {
/* 13 */     store(envelope, hint);
/* 14 */     return true;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   default void store(@NotNull SentryEnvelope envelope) {
/* 19 */     storeEnvelope(envelope, new Hint());
/*    */   }
/*    */   
/*    */   void discard(@NotNull SentryEnvelope paramSentryEnvelope);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\IEnvelopeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */