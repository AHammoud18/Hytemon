/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ public final class RequestDetails
/*    */ {
/*    */   @NotNull
/*    */   private final URL url;
/*    */   @NotNull
/*    */   private final Map<String, String> headers;
/*    */   
/*    */   public RequestDetails(@NotNull String url, @NotNull Map<String, String> headers) {
/* 19 */     Objects.requireNonNull(url, "url is required");
/* 20 */     Objects.requireNonNull(headers, "headers is required");
/*    */     try {
/* 22 */       this.url = URI.create(url).toURL();
/* 23 */     } catch (MalformedURLException e) {
/* 24 */       throw new IllegalArgumentException("Failed to compose the Sentry's server URL.", e);
/*    */     } 
/* 26 */     this.headers = headers;
/*    */   }
/*    */   @NotNull
/*    */   public URL getUrl() {
/* 30 */     return this.url;
/*    */   }
/*    */   @NotNull
/*    */   public Map<String, String> getHeaders() {
/* 34 */     return this.headers;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\RequestDetails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */