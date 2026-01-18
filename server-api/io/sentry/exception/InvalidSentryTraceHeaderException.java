/*    */ package io.sentry.exception;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InvalidSentryTraceHeaderException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -8353316997083420940L;
/*    */   @NotNull
/*    */   private final String sentryTraceHeader;
/*    */   
/*    */   public InvalidSentryTraceHeaderException(@NotNull String sentryTraceHeader) {
/* 16 */     this(sentryTraceHeader, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public InvalidSentryTraceHeaderException(@NotNull String sentryTraceHeader, @Nullable Throwable cause) {
/* 21 */     super("sentry-trace header does not conform to expected format: " + sentryTraceHeader, cause);
/* 22 */     this.sentryTraceHeader = sentryTraceHeader;
/*    */   }
/*    */   @NotNull
/*    */   public String getSentryTraceHeader() {
/* 26 */     return this.sentryTraceHeader;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\exception\InvalidSentryTraceHeaderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */