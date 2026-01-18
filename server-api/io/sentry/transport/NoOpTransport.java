/*    */ package io.sentry.transport;
/*    */ 
/*    */ import io.sentry.Hint;
/*    */ import io.sentry.SentryEnvelope;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpTransport
/*    */   implements ITransport {
/* 13 */   private static final NoOpTransport instance = new NoOpTransport();
/*    */   @NotNull
/*    */   public static NoOpTransport getInstance() {
/* 16 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void send(@NotNull SentryEnvelope envelope, @NotNull Hint hint) throws IOException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush(long timeoutMillis) {}
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public RateLimiter getRateLimiter() {
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {}
/*    */   
/*    */   public void close(boolean isRestarting) throws IOException {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\NoOpTransport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */