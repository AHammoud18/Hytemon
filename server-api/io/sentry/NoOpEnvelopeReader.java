/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpEnvelopeReader
/*    */   implements IEnvelopeReader {
/* 10 */   private static final NoOpEnvelopeReader instance = new NoOpEnvelopeReader();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpEnvelopeReader getInstance() {
/* 15 */     return instance;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SentryEnvelope read(@NotNull InputStream stream) throws IOException {
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpEnvelopeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */