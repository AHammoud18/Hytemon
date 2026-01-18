/*     */ package com.hypixel.hytale.server.core.auth.oauth;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.auth.AuthConfig;
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpServer;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import java.net.http.HttpClient;
/*     */ import java.net.http.HttpRequest;
/*     */ import java.net.http.HttpResponse;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.SecureRandom;
/*     */ import java.time.Duration;
/*     */ import java.util.Base64;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OAuthClient
/*     */ {
/*  38 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*  39 */   private static final SecureRandom RANDOM = new SecureRandom();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private final HttpClient httpClient = HttpClient.newBuilder()
/*  45 */     .connectTimeout(Duration.ofSeconds(10L))
/*  46 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable startFlow(@Nonnull OAuthBrowserFlow flow) {
/*  52 */     AtomicBoolean cancelled = new AtomicBoolean(false);
/*     */     
/*  54 */     CompletableFuture.runAsync(() -> {
/*     */           HttpServer server = null;
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*     */             String csrfState = generateRandomString(32);
/*     */ 
/*     */ 
/*     */             
/*     */             String codeVerifier = generateRandomString(64);
/*     */ 
/*     */ 
/*     */             
/*     */             String codeChallenge = generateCodeChallenge(codeVerifier);
/*     */ 
/*     */ 
/*     */             
/*     */             server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
/*     */ 
/*     */ 
/*     */             
/*     */             int port = server.getAddress().getPort();
/*     */ 
/*     */ 
/*     */             
/*     */             String encodedState = encodeStateWithPort(csrfState, port);
/*     */ 
/*     */ 
/*     */             
/*     */             String redirectUri = "https://accounts.hytale.com/consent/client";
/*     */ 
/*     */ 
/*     */             
/*     */             CompletableFuture<String> authCodeFuture = new CompletableFuture<>();
/*     */ 
/*     */ 
/*     */             
/*     */             HttpServer finalServer = server;
/*     */ 
/*     */ 
/*     */             
/*     */             String expectedState = csrfState;
/*     */ 
/*     */ 
/*     */             
/*     */             server.createContext("/", ());
/*     */ 
/*     */ 
/*     */             
/*     */             server.setExecutor(null);
/*     */ 
/*     */ 
/*     */             
/*     */             server.start();
/*     */ 
/*     */ 
/*     */             
/*     */             String authUrl = buildAuthUrl(encodedState, codeChallenge, redirectUri);
/*     */ 
/*     */ 
/*     */             
/*     */             flow.onFlowInfo(authUrl);
/*     */ 
/*     */ 
/*     */             
/*     */             String authCode = authCodeFuture.get(5L, TimeUnit.MINUTES);
/*     */ 
/*     */ 
/*     */             
/*     */             if (cancelled.get()) {
/*     */               flow.onFailure("Authentication cancelled");
/*     */ 
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             TokenResponse oauthTokens = exchangeCodeForTokens(authCode, codeVerifier, redirectUri);
/*     */ 
/*     */ 
/*     */             
/*     */             if (oauthTokens == null) {
/*     */               flow.onFailure("Failed to exchange authorization code for tokens");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */             
/*     */             flow.onSuccess(oauthTokens);
/* 147 */           } catch (Exception e) {
/*     */             ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("OAuth browser flow failed");
/*     */             
/*     */             if (!cancelled.get()) {
/*     */               flow.onFailure(e.getMessage());
/*     */             }
/*     */           } finally {
/*     */             if (server != null) {
/*     */               server.stop(0);
/*     */             }
/*     */           } 
/*     */         });
/* 159 */     return () -> cancelled.set(true);
/*     */   }
/*     */   public static final class DeviceAuthResponse extends Record { private final String deviceCode; private final String userCode; private final String verificationUri; private final String verificationUriComplete; private final int expiresIn; private final int interval;
/* 162 */     public DeviceAuthResponse(String deviceCode, String userCode, String verificationUri, String verificationUriComplete, int expiresIn, int interval) { this.deviceCode = deviceCode; this.userCode = userCode; this.verificationUri = verificationUri; this.verificationUriComplete = verificationUriComplete; this.expiresIn = expiresIn; this.interval = interval; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$DeviceAuthResponse;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #162	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$DeviceAuthResponse; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$DeviceAuthResponse;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #162	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$DeviceAuthResponse; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$DeviceAuthResponse;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #162	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$DeviceAuthResponse;
/* 162 */       //   0	8	1	o	Ljava/lang/Object; } public String deviceCode() { return this.deviceCode; } public String userCode() { return this.userCode; } public String verificationUri() { return this.verificationUri; } public String verificationUriComplete() { return this.verificationUriComplete; } public int expiresIn() { return this.expiresIn; } public int interval() { return this.interval; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable startFlow(OAuthDeviceFlow flow) {
/* 181 */     AtomicBoolean cancelled = new AtomicBoolean(false);
/*     */     
/* 183 */     CompletableFuture.runAsync(() -> {
/*     */           try {
/*     */             DeviceAuthResponse deviceAuth = requestDeviceAuthorization();
/*     */ 
/*     */             
/*     */             if (deviceAuth == null) {
/*     */               flow.onFailure("Failed to start device authorization");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             flow.onFlowInfo(deviceAuth.userCode(), deviceAuth.verificationUri(), deviceAuth.verificationUriComplete(), deviceAuth.expiresIn());
/*     */             
/*     */             int pollInterval = Math.max(deviceAuth.interval, 5);
/*     */             
/*     */             long deadline = System.currentTimeMillis() + deviceAuth.expiresIn * 1000L;
/*     */             
/*     */             while (System.currentTimeMillis() < deadline && !cancelled.get()) {
/*     */               Thread.sleep(pollInterval * 1000L);
/*     */               
/*     */               TokenResponse tokens = pollDeviceToken(deviceAuth.deviceCode);
/*     */               
/*     */               if (tokens != null) {
/*     */                 if (tokens.error != null) {
/*     */                   if ("authorization_pending".equals(tokens.error)) {
/*     */                     continue;
/*     */                   }
/*     */                   
/*     */                   if ("slow_down".equals(tokens.error)) {
/*     */                     pollInterval += 5;
/*     */                     
/*     */                     continue;
/*     */                   } 
/*     */                   
/*     */                   flow.onFailure("Device authorization failed: " + tokens.error);
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/*     */                 flow.onSuccess(tokens);
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */             
/*     */             if (cancelled.get()) {
/*     */               flow.onFailure("Authentication cancelled");
/*     */             } else {
/*     */               flow.onFailure("Device authorization expired");
/*     */             } 
/* 234 */           } catch (Exception e) {
/*     */             ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("OAuth device flow failed");
/*     */             
/*     */             if (!cancelled.get()) {
/*     */               flow.onFailure(e.getMessage());
/*     */             }
/*     */           } 
/*     */         });
/* 242 */     return () -> cancelled.set(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TokenResponse refreshTokens(@Nonnull String refreshToken) {
/*     */     try {
/* 250 */       String body = "grant_type=refresh_token&client_id=" + URLEncoder.encode("hytale-server", StandardCharsets.UTF_8) + "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://oauth.accounts.hytale.com/oauth2/token")).header("Content-Type", "application/x-www-form-urlencoded").header("User-Agent", AuthConfig.USER_AGENT).POST(HttpRequest.BodyPublishers.ofString(body)).build();
/*     */       
/* 259 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 261 */       if (response.statusCode() != 200) {
/* 262 */         LOGGER.at(Level.WARNING).log("Token refresh failed: HTTP %d - %s", response.statusCode(), response.body());
/* 263 */         return null;
/*     */       } 
/*     */       
/* 266 */       return parseTokenResponse(response.body());
/*     */     }
/* 268 */     catch (Exception e) {
/* 269 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Token refresh failed");
/* 270 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildAuthUrl(String state, String codeChallenge, String redirectUri) {
/* 277 */     return "https://oauth.accounts.hytale.com/oauth2/auth?response_type=code&client_id=" + URLEncoder.encode("hytale-server", StandardCharsets.UTF_8) + "&redirect_uri=" + 
/* 278 */       URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) + "&scope=" + 
/* 279 */       URLEncoder.encode(String.join(" ", (CharSequence[])AuthConfig.SCOPES), StandardCharsets.UTF_8) + "&state=" + 
/* 280 */       URLEncoder.encode(state, StandardCharsets.UTF_8) + "&code_challenge=" + 
/* 281 */       URLEncoder.encode(codeChallenge, StandardCharsets.UTF_8) + "&code_challenge_method=S256";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private TokenResponse exchangeCodeForTokens(String code, String codeVerifier, String redirectUri) {
/*     */     try {
/* 292 */       String body = "grant_type=authorization_code&client_id=" + URLEncoder.encode("hytale-server", StandardCharsets.UTF_8) + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) + "&code_verifier=" + URLEncoder.encode(codeVerifier, StandardCharsets.UTF_8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://oauth.accounts.hytale.com/oauth2/token")).header("Content-Type", "application/x-www-form-urlencoded").header("User-Agent", AuthConfig.USER_AGENT).POST(HttpRequest.BodyPublishers.ofString(body)).build();
/*     */       
/* 301 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 303 */       if (response.statusCode() != 200) {
/* 304 */         LOGGER.at(Level.WARNING).log("Token exchange failed: HTTP %d - %s", response.statusCode(), response.body());
/* 305 */         return null;
/*     */       } 
/*     */       
/* 308 */       return parseTokenResponse(response.body());
/*     */     }
/* 310 */     catch (Exception e) {
/* 311 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Token exchange failed");
/* 312 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private DeviceAuthResponse requestDeviceAuthorization() {
/*     */     try {
/* 320 */       String body = "client_id=" + URLEncoder.encode("hytale-server", StandardCharsets.UTF_8) + "&scope=" + URLEncoder.encode(String.join(" ", (CharSequence[])AuthConfig.SCOPES), StandardCharsets.UTF_8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 327 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://oauth.accounts.hytale.com/oauth2/device/auth")).header("Content-Type", "application/x-www-form-urlencoded").header("User-Agent", AuthConfig.USER_AGENT).POST(HttpRequest.BodyPublishers.ofString(body)).build();
/*     */       
/* 329 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 331 */       if (response.statusCode() != 200) {
/* 332 */         LOGGER.at(Level.WARNING).log("Device authorization request failed: HTTP %d - %s", response.statusCode(), response.body());
/* 333 */         return null;
/*     */       } 
/*     */       
/* 336 */       return parseDeviceAuthResponse(response.body());
/*     */     }
/* 338 */     catch (Exception e) {
/* 339 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Device authorization request failed");
/* 340 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private TokenResponse pollDeviceToken(String deviceCode) {
/*     */     try {
/* 349 */       String body = "grant_type=urn:ietf:params:oauth:grant-type:device_code&client_id=" + URLEncoder.encode("hytale-server", StandardCharsets.UTF_8) + "&device_code=" + URLEncoder.encode(deviceCode, StandardCharsets.UTF_8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 356 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://oauth.accounts.hytale.com/oauth2/token")).header("Content-Type", "application/x-www-form-urlencoded").header("User-Agent", AuthConfig.USER_AGENT).POST(HttpRequest.BodyPublishers.ofString(body)).build();
/*     */       
/* 358 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */ 
/*     */       
/* 361 */       if (response.statusCode() == 400) {
/* 362 */         return parseTokenResponse(response.body());
/*     */       }
/*     */       
/* 365 */       if (response.statusCode() != 200) {
/* 366 */         LOGGER.at(Level.WARNING).log("Device token poll failed: HTTP %d - %s", response.statusCode(), response.body());
/* 367 */         return null;
/*     */       } 
/*     */       
/* 370 */       return parseTokenResponse(response.body());
/*     */     }
/* 372 */     catch (Exception e) {
/* 373 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Device token poll failed");
/* 374 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String generateRandomString(int length) {
/* 379 */     byte[] bytes = new byte[length];
/* 380 */     RANDOM.nextBytes(bytes);
/* 381 */     return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
/*     */   }
/*     */   
/*     */   private String generateCodeChallenge(String verifier) {
/*     */     try {
/* 386 */       MessageDigest digest = MessageDigest.getInstance("SHA-256");
/* 387 */       byte[] hash = digest.digest(verifier.getBytes(StandardCharsets.US_ASCII));
/* 388 */       return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
/* 389 */     } catch (Exception e) {
/* 390 */       throw new RuntimeException("Failed to generate code challenge", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String extractParam(String query, String name) {
/* 395 */     if (query == null) return null; 
/* 396 */     Pattern pattern = Pattern.compile(name + "=([^&]*)");
/* 397 */     Matcher matcher = pattern.matcher(query);
/* 398 */     if (matcher.find()) {
/* 399 */       return URLDecoder.decode(matcher.group(1), StandardCharsets.UTF_8);
/*     */     }
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String encodeStateWithPort(String state, int port) {
/* 411 */     String json = String.format("{\"state\":\"%s\",\"port\":\"%d\"}", new Object[] { state, Integer.valueOf(port) });
/* 412 */     return Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes(StandardCharsets.UTF_8));
/*     */   }
/*     */   
/*     */   private TokenResponse parseTokenResponse(String json) {
/* 416 */     JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
/* 417 */     return new TokenResponse(
/* 418 */         getJsonString(obj, "access_token"), 
/* 419 */         getJsonString(obj, "refresh_token"), 
/* 420 */         getJsonString(obj, "id_token"), 
/* 421 */         getJsonString(obj, "error"), 
/* 422 */         getJsonInt(obj, "expires_in", 0));
/*     */   }
/*     */ 
/*     */   
/*     */   private DeviceAuthResponse parseDeviceAuthResponse(String json) {
/* 427 */     JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
/* 428 */     return new DeviceAuthResponse(
/* 429 */         getJsonString(obj, "device_code"), 
/* 430 */         getJsonString(obj, "user_code"), 
/* 431 */         getJsonString(obj, "verification_uri"), 
/* 432 */         getJsonString(obj, "verification_uri_complete"), 
/* 433 */         getJsonInt(obj, "expires_in", 600), 
/* 434 */         getJsonInt(obj, "interval", 5));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getJsonString(JsonObject obj, String key) {
/* 440 */     JsonElement elem = obj.get(key);
/* 441 */     return (elem != null && elem.isJsonPrimitive()) ? elem.getAsString() : null;
/*     */   }
/*     */   
/*     */   private static int getJsonInt(JsonObject obj, String key, int defaultValue) {
/* 445 */     JsonElement elem = obj.get(key);
/* 446 */     return (elem != null && elem.isJsonPrimitive()) ? elem.getAsInt() : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String buildHtmlPage(boolean success, String title, String heading, String message, @Nullable String errorDetail) {
/* 452 */     String detail = (errorDetail != null && !errorDetail.isEmpty()) ? ("<div class=\"error\">" + errorDetail + "</div>") : "";
/* 453 */     String iconClass = success ? "icon-success" : "icon-error";
/*     */ 
/*     */     
/* 456 */     String iconSvg = success ? "<polyline points=\"20 6 9 17 4 12\"></polyline>" : "<line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line><line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line>";
/* 457 */     return "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>%s - Hytale</title>\n    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n    <link href=\"https://fonts.googleapis.com/css2?family=Lexend:wght@700&family=Nunito+Sans:wght@400;700&display=swap\" rel=\"stylesheet\">\n    <style>\n        * { margin: 0; padding: 0; box-sizing: border-box; }\n        html { color-scheme: dark; background: linear-gradient(180deg, #15243A, #0F1418); min-height: 100vh; }\n        body { font-family: \"Nunito Sans\", sans-serif; color: #b7cedd; min-height: 100vh; display: flex; align-items: center; justify-content: center; padding: 20px; }\n        .card { background: rgba(0,0,0,0.4); border: 2px solid rgba(71,81,107,0.6); border-radius: 12px; padding: 48px 40px; max-width: 420px; text-align: center; }\n        .icon { width: 64px; height: 64px; margin: 0 auto 24px; border-radius: 50%%; display: flex; align-items: center; justify-content: center; }\n        .icon svg { width: 32px; height: 32px; }\n        .icon-success { background: linear-gradient(135deg, #2d5a3d, #1e3a2a); border: 2px solid #4a9d6b; }\n        .icon-success svg { color: #6fcf97; }\n        .icon-error { background: linear-gradient(135deg, #5a2d3d, #3a1e2a); border: 2px solid #c3194c; }\n        .icon-error svg { color: #ff6b8a; }\n        h1 { font-family: \"Lexend\", sans-serif; font-size: 1.5rem; text-transform: uppercase; background: linear-gradient(#f5fbff, #bfe6ff); -webkit-background-clip: text; background-clip: text; color: transparent; margin-bottom: 12px; }\n        p { line-height: 1.6; }\n        .error { background: rgba(195,25,76,0.15); border: 1px solid rgba(195,25,76,0.4); border-radius: 6px; padding: 12px; margin-top: 16px; color: #ff8fa8; font-size: 0.875rem; word-break: break-word; }\n    </style>\n</head>\n<body><div class=\"card\"><div class=\"icon %s\"><svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2.5\" stroke-linecap=\"round\" stroke-linejoin=\"round\">%s</svg></div><h1>%s</h1><p>%s</p>%s</div></body>\n</html>\n"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       .formatted(new Object[] { title, iconClass, iconSvg, heading, message, detail });
/*     */   } public static final class TokenResponse extends Record { @Nullable
/*     */     private final String accessToken; @Nullable
/*     */     private final String refreshToken; @Nullable
/*     */     private final String idToken; @Nullable
/*     */     private final String error; private final int expiresIn;
/* 491 */     public TokenResponse(@Nullable String accessToken, @Nullable String refreshToken, @Nullable String idToken, @Nullable String error, int expiresIn) { this.accessToken = accessToken; this.refreshToken = refreshToken; this.idToken = idToken; this.error = error; this.expiresIn = expiresIn; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$TokenResponse;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #491	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 491 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$TokenResponse; } @Nullable public String accessToken() { return this.accessToken; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$TokenResponse;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #491	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$TokenResponse; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$TokenResponse;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #491	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/auth/oauth/OAuthClient$TokenResponse;
/* 491 */       //   0	8	1	o	Ljava/lang/Object; } @Nullable public String refreshToken() { return this.refreshToken; } @Nullable public String idToken() { return this.idToken; } @Nullable public String error() { return this.error; } public int expiresIn() { return this.expiresIn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSuccess() {
/* 499 */       return (this.error == null && this.accessToken != null);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\oauth\OAuthClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */