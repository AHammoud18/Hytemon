/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
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
/*    */ 
/*    */ 
/*    */ public final class SystemOutLogger
/*    */   implements ILogger
/*    */ {
/*    */   public void log(@NotNull SentryLevel level, @NotNull String message, @Nullable Object... args) {
/* 24 */     System.out.println(String.format("%s: %s", new Object[] { level, String.format(message, args) }));
/*    */   }
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
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @NotNull String message, @Nullable Throwable throwable) {
/* 40 */     if (throwable == null) {
/* 41 */       log(level, message, new Object[0]);
/*    */     } else {
/* 43 */       System.out.println(
/* 44 */           String.format("%s: %s\n%s", new Object[] {
/*    */               
/* 46 */               level, String.format(message, new Object[] { throwable.toString() }), captureStackTrace(throwable)
/*    */             }));
/*    */     } 
/*    */   }
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
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @Nullable Throwable throwable, @NotNull String message, @Nullable Object... args) {
/* 65 */     if (throwable == null) {
/* 66 */       log(level, message, args);
/*    */     } else {
/* 68 */       System.out.println(
/* 69 */           String.format("%s: %s \n %s\n%s", new Object[] {
/*    */ 
/*    */               
/* 72 */               level, String.format(message, args), throwable
/* 73 */               .toString(), 
/* 74 */               captureStackTrace(throwable)
/*    */             }));
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isEnabled(@Nullable SentryLevel level) {
/* 80 */     return true;
/*    */   }
/*    */   @NotNull
/*    */   private String captureStackTrace(@NotNull Throwable throwable) {
/* 84 */     StringWriter stringWriter = new StringWriter();
/* 85 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/* 86 */     throwable.printStackTrace(printWriter);
/* 87 */     return stringWriter.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SystemOutLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */