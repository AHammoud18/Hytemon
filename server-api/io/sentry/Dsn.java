/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.net.URI;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Dsn
/*    */ {
/*    */   @NotNull
/*    */   private final String projectId;
/*    */   @Nullable
/*    */   private final String path;
/*    */   
/*    */   @NotNull
/*    */   public String getProjectId() {
/* 19 */     return this.projectId;
/*    */   } @Nullable
/*    */   private final String secretKey; @NotNull
/*    */   private final String publicKey; @NotNull
/*    */   private final URI sentryUri;
/*    */   @Nullable
/*    */   public String getPath() {
/* 26 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getSecretKey() {
/* 33 */     return this.secretKey;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getPublicKey() {
/* 40 */     return this.publicKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   URI getSentryUri() {
/* 48 */     return this.sentryUri;
/*    */   }
/*    */   
/*    */   Dsn(@Nullable String dsn) throws IllegalArgumentException {
/*    */     try {
/* 53 */       Objects.requireNonNull(dsn, "The DSN is required.");
/* 54 */       URI uri = (new URI(dsn)).normalize();
/* 55 */       String scheme = uri.getScheme();
/* 56 */       if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
/* 57 */         throw new IllegalArgumentException("Invalid DSN scheme: " + scheme);
/*    */       }
/*    */       
/* 60 */       String userInfo = uri.getUserInfo();
/* 61 */       if (userInfo == null || userInfo.isEmpty()) {
/* 62 */         throw new IllegalArgumentException("Invalid DSN: No public key provided.");
/*    */       }
/* 64 */       String[] keys = userInfo.split(":", -1);
/* 65 */       this.publicKey = keys[0];
/* 66 */       if (this.publicKey == null || this.publicKey.isEmpty()) {
/* 67 */         throw new IllegalArgumentException("Invalid DSN: No public key provided.");
/*    */       }
/* 69 */       this.secretKey = (keys.length > 1) ? keys[1] : null;
/* 70 */       String uriPath = uri.getPath();
/* 71 */       if (uriPath.endsWith("/")) {
/* 72 */         uriPath = uriPath.substring(0, uriPath.length() - 1);
/*    */       }
/* 74 */       int projectIdStart = uriPath.lastIndexOf("/") + 1;
/* 75 */       String path = uriPath.substring(0, projectIdStart);
/* 76 */       if (!path.endsWith("/")) {
/* 77 */         path = path + "/";
/*    */       }
/* 79 */       this.path = path;
/* 80 */       this.projectId = uriPath.substring(projectIdStart);
/* 81 */       if (this.projectId.isEmpty()) {
/* 82 */         throw new IllegalArgumentException("Invalid DSN: A Project Id is required.");
/*    */       }
/* 84 */       this
/*    */         
/* 86 */         .sentryUri = new URI(scheme, null, uri.getHost(), uri.getPort(), path + "api/" + this.projectId, null, null);
/* 87 */     } catch (Throwable e) {
/* 88 */       throw new IllegalArgumentException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Dsn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */