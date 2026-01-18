/*    */ package io.sentry.transport;
/*    */ 
/*    */ import io.sentry.Hint;
/*    */ import io.sentry.SentryEnvelope;
/*    */ import io.sentry.cache.IEnvelopeCache;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class NoOpEnvelopeCache implements IEnvelopeCache {
/* 11 */   private static final NoOpEnvelopeCache instance = new NoOpEnvelopeCache();
/*    */   
/*    */   public static NoOpEnvelopeCache getInstance() {
/* 14 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void store(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {}
/*    */ 
/*    */   
/*    */   public boolean storeEnvelope(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void discard(@NotNull SentryEnvelope envelope) {}
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Iterator<SentryEnvelope> iterator() {
/* 32 */     return Collections.emptyIterator();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\NoOpEnvelopeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */