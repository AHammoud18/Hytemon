/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.net.URI;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Experimental
/*    */ public final class RequestDetailsResolver
/*    */ {
/*    */   private static final String USER_AGENT = "User-Agent";
/*    */   private static final String SENTRY_AUTH = "X-Sentry-Auth";
/*    */   @NotNull
/*    */   private final Dsn dsn;
/*    */   @Nullable
/*    */   private final String sentryClientName;
/*    */   
/*    */   public RequestDetailsResolver(@NotNull String dsn, @Nullable String sentryClientName) {
/* 25 */     Objects.requireNonNull(dsn, "dsn is required");
/*    */     
/* 27 */     this.dsn = new Dsn(dsn);
/* 28 */     this.sentryClientName = sentryClientName;
/*    */   }
/*    */   
/*    */   @Internal
/*    */   public RequestDetailsResolver(@NotNull SentryOptions options) {
/* 33 */     Objects.requireNonNull(options, "options is required");
/*    */     
/* 35 */     this.dsn = options.retrieveParsedDsn();
/* 36 */     this.sentryClientName = options.getSentryClientName();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public RequestDetails resolve() {
/* 41 */     URI sentryUri = this.dsn.getSentryUri();
/* 42 */     String envelopeUrl = sentryUri.resolve(sentryUri.getPath() + "/envelope/").toString();
/*    */     
/* 44 */     String publicKey = this.dsn.getPublicKey();
/* 45 */     String secretKey = this.dsn.getSecretKey();
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
/* 56 */     String authHeader = "Sentry sentry_version=7,sentry_client=" + this.sentryClientName + ",sentry_key=" + publicKey + ((secretKey != null && secretKey.length() > 0) ? (",sentry_secret=" + secretKey) : "");
/*    */     
/* 58 */     Map<String, String> headers = new HashMap<>();
/* 59 */     headers.put("User-Agent", this.sentryClientName);
/* 60 */     headers.put("X-Sentry-Auth", authHeader);
/*    */     
/* 62 */     return new RequestDetails(envelopeUrl, headers);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\RequestDetailsResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */