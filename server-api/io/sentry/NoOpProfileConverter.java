/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.profiling.SentryProfile;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class NoOpProfileConverter
/*    */   implements IProfileConverter {
/*  9 */   private static final NoOpProfileConverter instance = new NoOpProfileConverter();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpProfileConverter getInstance() {
/* 14 */     return instance;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public SentryProfile convertFromFile(@NotNull String jfrFilePath) throws IOException {
/* 19 */     return new SentryProfile();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpProfileConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */