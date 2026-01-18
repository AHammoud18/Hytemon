/*     */ package com.hypixel.hytale.server.core.auth;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.EmptyExtraInfo;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.http.HttpClient;
/*     */ import java.net.http.HttpRequest;
/*     */ import java.net.http.HttpResponse;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class SessionServiceClient
/*     */ {
/*  28 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*  29 */   private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5L);
/*     */ 
/*     */   
/*     */   private final HttpClient httpClient;
/*     */ 
/*     */   
/*     */   private final String sessionServiceUrl;
/*     */ 
/*     */ 
/*     */   
/*     */   public SessionServiceClient(@Nonnull String sessionServiceUrl) {
/*  40 */     if (sessionServiceUrl == null || sessionServiceUrl.isEmpty()) {
/*  41 */       throw new IllegalArgumentException("Session Service URL cannot be null or empty");
/*     */     }
/*     */     
/*  44 */     this
/*     */       
/*  46 */       .sessionServiceUrl = sessionServiceUrl.endsWith("/") ? sessionServiceUrl.substring(0, sessionServiceUrl.length() - 1) : sessionServiceUrl;
/*     */     
/*  48 */     this
/*     */       
/*  50 */       .httpClient = HttpClient.newBuilder().connectTimeout(REQUEST_TIMEOUT).build();
/*     */     
/*  52 */     LOGGER.at(Level.INFO).log("Session Service client initialized for: %s", this.sessionServiceUrl);
/*     */   }
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
/*     */   public CompletableFuture<String> requestAuthorizationGrantAsync(@Nonnull String identityToken, @Nonnull String serverAudience, @Nonnull String bearerToken) {
/*  70 */     return CompletableFuture.supplyAsync(() -> {
/*     */           try {
/*     */             String jsonBody = String.format("{\"identityToken\":\"%s\",\"aud\":\"%s\"}", new Object[] { escapeJsonString(identityToken), escapeJsonString(serverAudience) });
/*     */ 
/*     */ 
/*     */             
/*     */             HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.sessionServiceUrl + "/server-join/auth-grant")).header("Content-Type", "application/json").header("Accept", "application/json").header("Authorization", "Bearer " + bearerToken).header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
/*     */ 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Requesting authorization grant with identity token, aud='%s'", serverAudience);
/*     */ 
/*     */ 
/*     */             
/*     */             HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */ 
/*     */ 
/*     */             
/*     */             if (response.statusCode() != 200) {
/*     */               LOGGER.at(Level.WARNING).log("Failed to request authorization grant: HTTP %d - %s", response.statusCode(), response.body());
/*     */ 
/*     */ 
/*     */               
/*     */               return null;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             AuthGrantResponse authGrantResponse = (AuthGrantResponse)AuthGrantResponse.CODEC.decodeJson(new RawJsonReader(((String)response.body()).toCharArray()), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */ 
/*     */ 
/*     */             
/*     */             if (authGrantResponse == null || authGrantResponse.authorizationGrant == null) {
/*     */               LOGGER.at(Level.WARNING).log("Session Service response missing authorizationGrant field");
/*     */ 
/*     */ 
/*     */               
/*     */               return null;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Successfully obtained authorization grant");
/*     */ 
/*     */ 
/*     */             
/*     */             return authGrantResponse.authorizationGrant;
/* 117 */           } catch (IOException e) {
/*     */             LOGGER.at(Level.WARNING).log("IO error while requesting authorization grant: %s", e.getMessage());
/*     */             return null;
/* 120 */           } catch (InterruptedException e) {
/*     */             LOGGER.at(Level.WARNING).log("Request interrupted while obtaining authorization grant");
/*     */             Thread.currentThread().interrupt();
/*     */             return null;
/* 124 */           } catch (Exception e) {
/*     */             LOGGER.at(Level.WARNING).log("Unexpected error requesting authorization grant: %s", e.getMessage());
/*     */             return null;
/*     */           } 
/*     */         });
/*     */   }
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
/*     */   public CompletableFuture<String> exchangeAuthGrantForTokenAsync(@Nonnull String authorizationGrant, @Nonnull String x509Fingerprint, @Nonnull String bearerToken) {
/* 147 */     return CompletableFuture.supplyAsync(() -> {
/*     */           try {
/*     */             String jsonBody = String.format("{\"authorizationGrant\":\"%s\",\"x509Fingerprint\":\"%s\"}", new Object[] { escapeJsonString(authorizationGrant), escapeJsonString(x509Fingerprint) });
/*     */ 
/*     */ 
/*     */             
/*     */             HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.sessionServiceUrl + "/server-join/auth-token")).header("Content-Type", "application/json").header("Accept", "application/json").header("Authorization", "Bearer " + bearerToken).header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
/*     */ 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Exchanging authorization grant for access token");
/*     */ 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.FINE).log("Using bearer token (first 20 chars): %s...", (bearerToken.length() > 20) ? bearerToken.substring(0, 20) : bearerToken);
/*     */ 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.FINE).log("Request body: %s", jsonBody);
/*     */ 
/*     */ 
/*     */             
/*     */             HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */ 
/*     */ 
/*     */             
/*     */             if (response.statusCode() != 200) {
/*     */               LOGGER.at(Level.WARNING).log("Failed to exchange auth grant: HTTP %d - %s", response.statusCode(), response.body());
/*     */ 
/*     */ 
/*     */               
/*     */               return null;
/*     */             } 
/*     */ 
/*     */             
/*     */             AccessTokenResponse tokenResponse = (AccessTokenResponse)AccessTokenResponse.CODEC.decodeJson(new RawJsonReader(((String)response.body()).toCharArray()), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */ 
/*     */             
/*     */             if (tokenResponse == null || tokenResponse.accessToken == null) {
/*     */               LOGGER.at(Level.WARNING).log("Session Service response missing accessToken field");
/*     */ 
/*     */               
/*     */               return null;
/*     */             } 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Successfully obtained access token");
/*     */ 
/*     */             
/*     */             return tokenResponse.accessToken;
/* 197 */           } catch (IOException e) {
/*     */             LOGGER.at(Level.WARNING).log("IO error while exchanging auth grant: %s", e.getMessage());
/*     */             return null;
/* 200 */           } catch (InterruptedException e) {
/*     */             LOGGER.at(Level.WARNING).log("Request interrupted while exchanging auth grant");
/*     */             Thread.currentThread().interrupt();
/*     */             return null;
/* 204 */           } catch (Exception e) {
/*     */             LOGGER.at(Level.WARNING).log("Unexpected error exchanging auth grant: %s", e.getMessage());
/*     */             return null;
/*     */           } 
/*     */         });
/*     */   }
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
/*     */   @Nullable
/*     */   public JwksResponse getJwks() {
/*     */     try {
/* 225 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.sessionServiceUrl + "/.well-known/jwks.json")).header("Accept", "application/json").header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).GET().build();
/*     */       
/* 227 */       LOGGER.at(Level.FINE).log("Fetching JWKS from Session Service");
/*     */       
/* 229 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 231 */       if (response.statusCode() != 200) {
/* 232 */         LOGGER.at(Level.WARNING).log("Failed to fetch JWKS: HTTP %d - %s", response
/*     */             
/* 234 */             .statusCode(), response.body());
/*     */         
/* 236 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 240 */       JwksResponse jwks = (JwksResponse)JwksResponse.CODEC.decodeJson(new RawJsonReader(((String)response
/* 241 */             .body()).toCharArray()), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */ 
/*     */ 
/*     */       
/* 245 */       if (jwks == null || jwks.keys == null || jwks.keys.length == 0) {
/* 246 */         LOGGER.at(Level.WARNING).log("Session Service returned invalid JWKS (no keys)");
/* 247 */         return null;
/*     */       } 
/*     */       
/* 250 */       LOGGER.at(Level.INFO).log("Successfully fetched JWKS with %d keys", jwks.keys.length);
/* 251 */       return jwks;
/*     */     }
/* 253 */     catch (IOException e) {
/* 254 */       LOGGER.at(Level.WARNING).log("IO error while fetching JWKS: %s", e.getMessage());
/* 255 */       return null;
/* 256 */     } catch (InterruptedException e) {
/* 257 */       LOGGER.at(Level.WARNING).log("Request interrupted while fetching JWKS");
/* 258 */       Thread.currentThread().interrupt();
/* 259 */       return null;
/* 260 */     } catch (Exception e) {
/* 261 */       LOGGER.at(Level.WARNING).log("Unexpected error fetching JWKS: %s", e.getMessage());
/* 262 */       return null;
/*     */     } 
/*     */   }
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
/*     */   @Nullable
/*     */   public GameProfile[] getGameProfiles(@Nonnull String oauthAccessToken) {
/*     */     try {
/* 283 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://account-data.hytale.com/my-account/get-profiles")).header("Accept", "application/json").header("Authorization", "Bearer " + oauthAccessToken).header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).GET().build();
/*     */       
/* 285 */       LOGGER.at(Level.INFO).log("Fetching game profiles...");
/*     */       
/* 287 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 289 */       if (response.statusCode() != 200) {
/* 290 */         LOGGER.at(Level.WARNING).log("Failed to fetch profiles: HTTP %d - %s", response
/*     */             
/* 292 */             .statusCode(), response.body());
/*     */         
/* 294 */         return null;
/*     */       } 
/*     */       
/* 297 */       LauncherDataResponse data = (LauncherDataResponse)LauncherDataResponse.CODEC.decodeJson(new RawJsonReader(((String)response
/* 298 */             .body()).toCharArray()), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */ 
/*     */ 
/*     */       
/* 302 */       if (data == null || data.profiles == null) {
/* 303 */         LOGGER.at(Level.WARNING).log("Account Data returned invalid response");
/* 304 */         return null;
/*     */       } 
/*     */       
/* 307 */       LOGGER.at(Level.INFO).log("Found %d game profile(s)", data.profiles.length);
/* 308 */       return data.profiles;
/*     */     }
/* 310 */     catch (IOException e) {
/* 311 */       LOGGER.at(Level.WARNING).log("IO error while fetching profiles: %s", e.getMessage());
/* 312 */       return null;
/* 313 */     } catch (InterruptedException e) {
/* 314 */       LOGGER.at(Level.WARNING).log("Request interrupted while fetching profiles");
/* 315 */       Thread.currentThread().interrupt();
/* 316 */       return null;
/* 317 */     } catch (Exception e) {
/* 318 */       LOGGER.at(Level.WARNING).log("Unexpected error fetching profiles: %s", e.getMessage());
/* 319 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameSessionResponse createGameSession(@Nonnull String oauthAccessToken, @Nonnull UUID profileUuid) {
/*     */     try {
/* 333 */       String body = String.format("{\"uuid\":\"%s\"}", new Object[] { profileUuid.toString() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.sessionServiceUrl + "/game-session/new")).header("Content-Type", "application/json").header("Authorization", "Bearer " + oauthAccessToken).header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).POST(HttpRequest.BodyPublishers.ofString(body)).build();
/*     */       
/* 344 */       LOGGER.at(Level.INFO).log("Creating game session...");
/*     */       
/* 346 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 348 */       if (response.statusCode() != 200 && response.statusCode() != 201) {
/* 349 */         LOGGER.at(Level.WARNING).log("Failed to create game session: HTTP %d - %s", response
/*     */             
/* 351 */             .statusCode(), response.body());
/*     */         
/* 353 */         return null;
/*     */       } 
/*     */       
/* 356 */       GameSessionResponse sessionResponse = (GameSessionResponse)GameSessionResponse.CODEC.decodeJson(new RawJsonReader(((String)response
/* 357 */             .body()).toCharArray()), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */ 
/*     */ 
/*     */       
/* 361 */       if (sessionResponse == null || sessionResponse.identityToken == null) {
/* 362 */         LOGGER.at(Level.WARNING).log("Session Service returned invalid response");
/* 363 */         return null;
/*     */       } 
/*     */       
/* 366 */       LOGGER.at(Level.INFO).log("Successfully created game session");
/* 367 */       return sessionResponse;
/*     */     }
/* 369 */     catch (IOException e) {
/* 370 */       LOGGER.at(Level.WARNING).log("IO error while creating session: %s", e.getMessage());
/* 371 */       return null;
/* 372 */     } catch (InterruptedException e) {
/* 373 */       LOGGER.at(Level.WARNING).log("Request interrupted while creating session");
/* 374 */       Thread.currentThread().interrupt();
/* 375 */       return null;
/* 376 */     } catch (Exception e) {
/* 377 */       LOGGER.at(Level.WARNING).log("Unexpected error creating session: %s", e.getMessage());
/* 378 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<GameSessionResponse> refreshSessionAsync(@Nonnull String sessionToken) {
/* 390 */     return CompletableFuture.supplyAsync(() -> {
/*     */           try {
/*     */             HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.sessionServiceUrl + "/game-session/refresh")).header("Accept", "application/json").header("Authorization", "Bearer " + sessionToken).header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).POST(HttpRequest.BodyPublishers.noBody()).build();
/*     */ 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Refreshing game session...");
/*     */ 
/*     */ 
/*     */             
/*     */             HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */ 
/*     */             
/*     */             if (response.statusCode() != 200) {
/*     */               LOGGER.at(Level.WARNING).log("Failed to refresh session: HTTP %d - %s", response.statusCode(), response.body());
/*     */ 
/*     */               
/*     */               return null;
/*     */             } 
/*     */ 
/*     */             
/*     */             GameSessionResponse sessionResponse = (GameSessionResponse)GameSessionResponse.CODEC.decodeJson(new RawJsonReader(((String)response.body()).toCharArray()), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */ 
/*     */             
/*     */             if (sessionResponse == null || sessionResponse.identityToken == null) {
/*     */               LOGGER.at(Level.WARNING).log("Session Service returned invalid response (missing identity token)");
/*     */ 
/*     */               
/*     */               return null;
/*     */             } 
/*     */ 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Successfully refreshed game session");
/*     */ 
/*     */             
/*     */             return sessionResponse;
/* 426 */           } catch (IOException e) {
/*     */             LOGGER.at(Level.WARNING).log("IO error while refreshing session: %s", e.getMessage());
/*     */             return null;
/* 429 */           } catch (InterruptedException e) {
/*     */             LOGGER.at(Level.WARNING).log("Request interrupted while refreshing session");
/*     */             Thread.currentThread().interrupt();
/*     */             return null;
/* 433 */           } catch (Exception e) {
/*     */             LOGGER.at(Level.WARNING).log("Unexpected error refreshing session: %s", e.getMessage());
/*     */             return null;
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminateSession(@Nonnull String sessionToken) {
/* 447 */     if (sessionToken == null || sessionToken.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 458 */       HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.sessionServiceUrl + "/game-session")).header("Authorization", "Bearer " + sessionToken).header("User-Agent", AuthConfig.USER_AGENT).timeout(REQUEST_TIMEOUT).DELETE().build();
/*     */       
/* 460 */       LOGGER.at(Level.INFO).log("Terminating game session...");
/*     */       
/* 462 */       HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*     */       
/* 464 */       if (response.statusCode() == 200 || response.statusCode() == 204) {
/* 465 */         LOGGER.at(Level.INFO).log("Game session terminated");
/*     */       } else {
/* 467 */         LOGGER.at(Level.WARNING).log("Failed to terminate session: HTTP %d - %s", response
/* 468 */             .statusCode(), response.body());
/*     */       } 
/* 470 */     } catch (IOException e) {
/* 471 */       LOGGER.at(Level.WARNING).log("IO error while terminating session: %s", e.getMessage());
/* 472 */     } catch (InterruptedException e) {
/* 473 */       LOGGER.at(Level.WARNING).log("Request interrupted while terminating session");
/* 474 */       Thread.currentThread().interrupt();
/* 475 */     } catch (Exception e) {
/* 476 */       LOGGER.at(Level.WARNING).log("Error terminating session: %s", e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String escapeJsonString(String value) {
/* 484 */     if (value == null) return ""; 
/* 485 */     return value
/* 486 */       .replace("\\", "\\\\")
/* 487 */       .replace("\"", "\\\"")
/* 488 */       .replace("\n", "\\n")
/* 489 */       .replace("\r", "\\r")
/* 490 */       .replace("\t", "\\t");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> KeyedCodec<T> externalKey(String key, Codec<T> codec) {
/* 496 */     return new KeyedCodec(key, codec, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AuthGrantResponse
/*     */   {
/*     */     public String authorizationGrant;
/*     */ 
/*     */     
/*     */     public static final BuilderCodec<AuthGrantResponse> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 511 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AuthGrantResponse.class, AuthGrantResponse::new).append(SessionServiceClient.externalKey("authorizationGrant", (Codec<?>)Codec.STRING), (r, v) -> r.authorizationGrant = v, r -> r.authorizationGrant).add()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AccessTokenResponse
/*     */   {
/*     */     public String accessToken;
/*     */ 
/*     */     
/*     */     public static final BuilderCodec<AccessTokenResponse> CODEC;
/*     */ 
/*     */     
/*     */     static {
/* 526 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AccessTokenResponse.class, AccessTokenResponse::new).append(SessionServiceClient.externalKey("accessToken", (Codec<?>)Codec.STRING), (r, v) -> r.accessToken = v, r -> r.accessToken).add()).build();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GameSessionResponse
/*     */   {
/*     */     public String sessionToken;
/*     */     public String identityToken;
/*     */     public String expiresAt;
/*     */     public static final BuilderCodec<GameSessionResponse> CODEC;
/*     */     
/*     */     public Instant getExpiresAtInstant() {
/* 538 */       if (this.expiresAt == null) return null; 
/*     */       try {
/* 540 */         return Instant.parse(this.expiresAt);
/* 541 */       } catch (Exception e) {
/* 542 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 550 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GameSessionResponse.class, GameSessionResponse::new).append(SessionServiceClient.externalKey("sessionToken", (Codec<?>)Codec.STRING), (r, v) -> r.sessionToken = v, r -> r.sessionToken).add()).append(SessionServiceClient.externalKey("identityToken", (Codec<?>)Codec.STRING), (r, v) -> r.identityToken = v, r -> r.identityToken).add()).append(SessionServiceClient.externalKey("expiresAt", (Codec<?>)Codec.STRING), (r, v) -> r.expiresAt = v, r -> r.expiresAt).add()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class JwksResponse
/*     */   {
/*     */     public SessionServiceClient.JwkKey[] keys;
/*     */ 
/*     */     
/*     */     public static final BuilderCodec<JwksResponse> CODEC;
/*     */ 
/*     */     
/*     */     static {
/* 565 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(JwksResponse.class, JwksResponse::new).append(SessionServiceClient.externalKey("keys", (Codec<?>)new ArrayCodec((Codec)SessionServiceClient.JwkKey.CODEC, x$0 -> new SessionServiceClient.JwkKey[x$0])), (r, v) -> r.keys = v, r -> r.keys).add()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class JwkKey
/*     */   {
/*     */     public String kty;
/*     */     
/*     */     public String alg;
/*     */     
/*     */     public String use;
/*     */     
/*     */     public String kid;
/*     */     
/*     */     public String crv;
/*     */     
/*     */     public String x;
/*     */     
/*     */     public String y;
/*     */     
/*     */     public String n;
/*     */     
/*     */     public String e;
/*     */     public static final BuilderCodec<JwkKey> CODEC;
/*     */     
/*     */     static {
/* 592 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(JwkKey.class, JwkKey::new).append(SessionServiceClient.externalKey("kty", (Codec<?>)Codec.STRING), (k, v) -> k.kty = v, k -> k.kty).add()).append(SessionServiceClient.externalKey("alg", (Codec<?>)Codec.STRING), (k, v) -> k.alg = v, k -> k.alg).add()).append(SessionServiceClient.externalKey("use", (Codec<?>)Codec.STRING), (k, v) -> k.use = v, k -> k.use).add()).append(SessionServiceClient.externalKey("kid", (Codec<?>)Codec.STRING), (k, v) -> k.kid = v, k -> k.kid).add()).append(SessionServiceClient.externalKey("crv", (Codec<?>)Codec.STRING), (k, v) -> k.crv = v, k -> k.crv).add()).append(SessionServiceClient.externalKey("x", (Codec<?>)Codec.STRING), (k, v) -> k.x = v, k -> k.x).add()).append(SessionServiceClient.externalKey("y", (Codec<?>)Codec.STRING), (k, v) -> k.y = v, k -> k.y).add()).append(SessionServiceClient.externalKey("n", (Codec<?>)Codec.STRING), (k, v) -> k.n = v, k -> k.n).add()).append(SessionServiceClient.externalKey("e", (Codec<?>)Codec.STRING), (k, v) -> k.e = v, k -> k.e).add()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LauncherDataResponse
/*     */   {
/*     */     public UUID owner;
/*     */ 
/*     */     
/*     */     public SessionServiceClient.GameProfile[] profiles;
/*     */     
/*     */     public static final BuilderCodec<LauncherDataResponse> CODEC;
/*     */ 
/*     */     
/*     */     static {
/* 609 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LauncherDataResponse.class, LauncherDataResponse::new).append(SessionServiceClient.externalKey("owner", (Codec<?>)Codec.UUID_STRING), (r, v) -> r.owner = v, r -> r.owner).add()).append(SessionServiceClient.externalKey("profiles", (Codec<?>)new ArrayCodec((Codec)SessionServiceClient.GameProfile.CODEC, x$0 -> new SessionServiceClient.GameProfile[x$0])), (r, v) -> r.profiles = v, r -> r.profiles).add()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GameProfile
/*     */   {
/*     */     public UUID uuid;
/*     */     
/*     */     public String username;
/*     */     public static final BuilderCodec<GameProfile> CODEC;
/*     */     
/*     */     static {
/* 622 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GameProfile.class, GameProfile::new).append(SessionServiceClient.externalKey("uuid", (Codec<?>)Codec.UUID_STRING), (p, v) -> p.uuid = v, p -> p.uuid).add()).append(SessionServiceClient.externalKey("username", (Codec<?>)Codec.STRING), (p, v) -> p.username = v, p -> p.username).add()).build();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\SessionServiceClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */