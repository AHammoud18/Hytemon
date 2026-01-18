/*    */ package io.sentry.transport;
/*    */ 
/*    */ import io.sentry.Hint;
/*    */ import io.sentry.ISerializer;
/*    */ import io.sentry.SentryEnvelope;
/*    */ import io.sentry.util.Objects;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class StdoutTransport implements ITransport {
/*    */   @NotNull
/*    */   private final ISerializer serializer;
/*    */   
/*    */   public StdoutTransport(@NotNull ISerializer serializer) {
/* 16 */     this.serializer = (ISerializer)Objects.requireNonNull(serializer, "Serializer is required");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void send(@NotNull SentryEnvelope envelope, @NotNull Hint hint) throws IOException {
/* 22 */     Objects.requireNonNull(envelope, "SentryEnvelope is required");
/*    */     
/*    */     try {
/* 25 */       this.serializer.serialize(envelope, System.out);
/* 26 */     } catch (Throwable throwable) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush(long timeoutMillis) {
/* 34 */     System.out.println("Flushing");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public RateLimiter getRateLimiter() {
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public void close(boolean isRestarting) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\StdoutTransport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */