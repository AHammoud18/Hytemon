/*    */ package io.sentry;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class DsnUtil
/*    */ {
/*    */   public static boolean urlContainsDsnHost(@Nullable SentryOptions options, @Nullable String url) {
/* 13 */     if (options == null) {
/* 14 */       return false;
/*    */     }
/*    */     
/* 17 */     if (url == null) {
/* 18 */       return false;
/*    */     }
/*    */     
/* 21 */     String dsnString = options.getDsn();
/* 22 */     if (dsnString == null) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     Dsn dsn = options.retrieveParsedDsn();
/* 27 */     URI sentryUri = dsn.getSentryUri();
/* 28 */     String dsnHost = sentryUri.getHost();
/*    */     
/* 30 */     if (dsnHost == null) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     String lowerCaseHost = dsnHost.toLowerCase(Locale.ROOT);
/* 35 */     int dsnPort = sentryUri.getPort();
/*    */     
/* 37 */     if (dsnPort > 0) {
/* 38 */       return url.toLowerCase(Locale.ROOT).contains(lowerCaseHost + ":" + dsnPort);
/*    */     }
/* 40 */     return url.toLowerCase(Locale.ROOT).contains(lowerCaseHost);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DsnUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */