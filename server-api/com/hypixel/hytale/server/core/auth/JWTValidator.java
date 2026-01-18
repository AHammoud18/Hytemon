/*     */ package com.hypixel.hytale.server.core.auth;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSVerifier;
/*     */ import com.nimbusds.jose.crypto.Ed25519Verifier;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jwt.JWTClaimsSet;
/*     */ import com.nimbusds.jwt.SignedJWT;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.ParseException;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JWTValidator
/*     */ {
/*  35 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*  38 */   private static final JWSAlgorithm SUPPORTED_ALGORITHM = JWSAlgorithm.EdDSA;
/*     */   
/*     */   private final SessionServiceClient sessionServiceClient;
/*     */   
/*     */   private final String expectedIssuer;
/*     */   
/*     */   private final String expectedAudience;
/*     */   private volatile JWKSet cachedJwkSet;
/*     */   private volatile long jwksCacheExpiry;
/*  47 */   private final long jwksCacheDurationMs = TimeUnit.HOURS.toMillis(1L);
/*  48 */   private final ReentrantLock jwksFetchLock = new ReentrantLock();
/*  49 */   private volatile CompletableFuture<JWKSet> pendingFetch = null;
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
/*     */   public JWTValidator(@Nonnull SessionServiceClient sessionServiceClient, @Nonnull String expectedIssuer, @Nonnull String expectedAudience) {
/*  63 */     this.sessionServiceClient = sessionServiceClient;
/*  64 */     this.expectedIssuer = expectedIssuer;
/*  65 */     this.expectedAudience = expectedAudience;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public JWTClaims validateToken(@Nonnull String accessToken, @Nullable X509Certificate clientCert) {
/*  77 */     if (accessToken.isEmpty()) {
/*  78 */       LOGGER.at(Level.WARNING).log("Access token is empty");
/*  79 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  84 */       SignedJWT signedJWT = SignedJWT.parse(accessToken);
/*     */ 
/*     */       
/*  87 */       JWSAlgorithm algorithm = signedJWT.getHeader().getAlgorithm();
/*  88 */       if (!SUPPORTED_ALGORITHM.equals(algorithm)) {
/*  89 */         LOGGER.at(Level.WARNING).log("Unsupported JWT algorithm: %s (expected EdDSA)", algorithm);
/*  90 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*  94 */       if (!verifySignatureWithRetry(signedJWT)) {
/*  95 */         LOGGER.at(Level.WARNING).log("JWT signature verification failed");
/*  96 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 100 */       JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
/* 101 */       JWTClaims claims = new JWTClaims();
/* 102 */       claims.issuer = claimsSet.getIssuer();
/* 103 */       claims
/* 104 */         .audience = (claimsSet.getAudience() != null && !claimsSet.getAudience().isEmpty()) ? claimsSet.getAudience().get(0) : null;
/* 105 */       claims.subject = claimsSet.getSubject();
/*     */       
/* 107 */       claims.username = claimsSet.getStringClaim("username");
/*     */       
/* 109 */       claims.ipAddress = claimsSet.getStringClaim("ip");
/* 110 */       claims
/* 111 */         .issuedAt = (claimsSet.getIssueTime() != null) ? Long.valueOf(claimsSet.getIssueTime().toInstant().getEpochSecond()) : null;
/* 112 */       claims
/* 113 */         .expiresAt = (claimsSet.getExpirationTime() != null) ? Long.valueOf(claimsSet.getExpirationTime().toInstant().getEpochSecond()) : null;
/* 114 */       claims
/* 115 */         .notBefore = (claimsSet.getNotBeforeTime() != null) ? Long.valueOf(claimsSet.getNotBeforeTime().toInstant().getEpochSecond()) : null;
/*     */ 
/*     */       
/* 118 */       Map<String, Object> cnfClaim = claimsSet.getJSONObjectClaim("cnf");
/* 119 */       if (cnfClaim != null) {
/* 120 */         claims.certificateFingerprint = (String)cnfClaim.get("x5t#S256");
/*     */       }
/*     */ 
/*     */       
/* 124 */       if (!this.expectedIssuer.equals(claims.issuer)) {
/* 125 */         LOGGER.at(Level.WARNING).log("Invalid issuer: expected %s, got %s", this.expectedIssuer, claims.issuer);
/* 126 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 130 */       if (!this.expectedAudience.equals(claims.audience)) {
/* 131 */         LOGGER.at(Level.WARNING).log("Invalid audience: expected %s, got %s", this.expectedAudience, claims.audience);
/* 132 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 136 */       long nowSeconds = Instant.now().getEpochSecond();
/* 137 */       long clockSkewSeconds = 60L;
/*     */       
/* 139 */       if (claims.expiresAt != null && nowSeconds >= claims.expiresAt.longValue() + clockSkewSeconds) {
/* 140 */         LOGGER.at(Level.WARNING).log("Token expired (exp: %d, now: %d)", claims.expiresAt, nowSeconds);
/* 141 */         return null;
/*     */       } 
/*     */       
/* 144 */       if (claims.notBefore != null && nowSeconds < claims.notBefore.longValue() - clockSkewSeconds) {
/* 145 */         LOGGER.at(Level.WARNING).log("Token not yet valid (nbf: %d, now: %d)", claims.notBefore, nowSeconds);
/* 146 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 150 */       if (!CertificateUtil.validateCertificateBinding(claims.certificateFingerprint, clientCert)) {
/* 151 */         LOGGER.at(Level.WARNING).log("Certificate binding validation failed");
/* 152 */         return null;
/*     */       } 
/*     */       
/* 155 */       LOGGER.at(Level.INFO).log("JWT validated successfully for user %s (UUID: %s)", claims.username, claims.subject);
/* 156 */       return claims;
/*     */     }
/* 158 */     catch (ParseException e) {
/* 159 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to parse JWT");
/* 160 */       return null;
/* 161 */     } catch (Exception e) {
/* 162 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("JWT validation error");
/* 163 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifySignature(SignedJWT signedJWT, JWKSet jwkSet) {
/*     */     try {
/* 172 */       String keyId = signedJWT.getHeader().getKeyID();
/*     */ 
/*     */       
/* 175 */       OctetKeyPair ed25519Key = null;
/* 176 */       for (JWK jwk : jwkSet.getKeys()) {
/* 177 */         if (jwk instanceof OctetKeyPair) { OctetKeyPair okp = (OctetKeyPair)jwk; if (keyId == null || keyId
/* 178 */             .equals(jwk.getKeyID())) {
/* 179 */             ed25519Key = okp;
/*     */             break;
/*     */           }  }
/*     */       
/*     */       } 
/* 184 */       if (ed25519Key == null) {
/* 185 */         LOGGER.at(Level.WARNING).log("No Ed25519 key found for kid=%s", keyId);
/* 186 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 190 */       Ed25519Verifier verifier = new Ed25519Verifier(ed25519Key);
/* 191 */       boolean valid = signedJWT.verify((JWSVerifier)verifier);
/*     */       
/* 193 */       if (valid) {
/* 194 */         LOGGER.at(Level.FINE).log("JWT signature verified with key kid=%s", keyId);
/*     */       }
/* 196 */       return valid;
/*     */     }
/* 198 */     catch (Exception e) {
/* 199 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("JWT signature verification failed");
/* 200 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private JWKSet getJwkSet() {
/* 209 */     return getJwkSet(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private JWKSet getJwkSet(boolean forceRefresh) {
/* 220 */     long now = System.currentTimeMillis();
/*     */ 
/*     */     
/* 223 */     if (!forceRefresh && this.cachedJwkSet != null && now < this.jwksCacheExpiry) {
/* 224 */       return this.cachedJwkSet;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 229 */     this.jwksFetchLock.lock();
/*     */     
/*     */     try {
/* 232 */       if (!forceRefresh && this.cachedJwkSet != null && now < this.jwksCacheExpiry) {
/* 233 */         return this.cachedJwkSet;
/*     */       }
/*     */ 
/*     */       
/* 237 */       CompletableFuture<JWKSet> existing = this.pendingFetch;
/* 238 */       if (existing != null && !existing.isDone()) {
/*     */         
/* 240 */         this.jwksFetchLock.unlock();
/*     */         try {
/* 242 */           return existing.join();
/*     */         } finally {
/* 244 */           this.jwksFetchLock.lock();
/*     */         } 
/*     */       } 
/*     */       
/* 248 */       if (forceRefresh) {
/* 249 */         LOGGER.at(Level.INFO).log("Force refreshing JWKS cache (key rotation or verification failure)");
/*     */       }
/*     */ 
/*     */       
/* 253 */       this.pendingFetch = CompletableFuture.supplyAsync(this::fetchJwksFromService);
/*     */     } finally {
/*     */       
/* 256 */       this.jwksFetchLock.unlock();
/*     */     } 
/*     */ 
/*     */     
/* 260 */     return this.pendingFetch.join();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private JWKSet fetchJwksFromService() {
/* 269 */     SessionServiceClient.JwksResponse jwksResponse = this.sessionServiceClient.getJwks();
/* 270 */     if (jwksResponse == null || jwksResponse.keys == null || jwksResponse.keys.length == 0) {
/* 271 */       LOGGER.at(Level.WARNING).log("Failed to fetch JWKS or no keys available");
/* 272 */       return this.cachedJwkSet;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 277 */       ArrayList<JWK> jwkList = new ArrayList<>();
/* 278 */       for (SessionServiceClient.JwkKey key : jwksResponse.keys) {
/* 279 */         JWK jwk = convertToJWK(key);
/* 280 */         if (jwk != null) {
/* 281 */           jwkList.add(jwk);
/*     */         }
/*     */       } 
/*     */       
/* 285 */       if (jwkList.isEmpty()) {
/* 286 */         LOGGER.at(Level.WARNING).log("No valid JWKs found in JWKS response");
/* 287 */         return this.cachedJwkSet;
/*     */       } 
/*     */       
/* 290 */       JWKSet newSet = new JWKSet(jwkList);
/* 291 */       this.cachedJwkSet = newSet;
/* 292 */       this.jwksCacheExpiry = System.currentTimeMillis() + this.jwksCacheDurationMs;
/*     */       
/* 294 */       LOGGER.at(Level.INFO).log("JWKS loaded with %d keys", jwkList.size());
/* 295 */       return newSet;
/*     */     }
/* 297 */     catch (Exception e) {
/* 298 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to parse JWKS");
/* 299 */       return this.cachedJwkSet;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifySignatureWithRetry(SignedJWT signedJWT) {
/* 309 */     JWKSet jwkSet = getJwkSet();
/* 310 */     if (jwkSet == null) {
/* 311 */       return false;
/*     */     }
/*     */     
/* 314 */     if (verifySignature(signedJWT, jwkSet)) {
/* 315 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 319 */     LOGGER.at(Level.INFO).log("Signature verification failed with cached JWKS, retrying with fresh keys");
/* 320 */     JWKSet freshJwkSet = getJwkSet(true);
/* 321 */     if (freshJwkSet == null || freshJwkSet == jwkSet)
/*     */     {
/* 323 */       return false;
/*     */     }
/*     */     
/* 326 */     return verifySignature(signedJWT, freshJwkSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private JWK convertToJWK(SessionServiceClient.JwkKey key) {
/* 335 */     if (!"OKP".equals(key.kty)) {
/* 336 */       LOGGER.at(Level.WARNING).log("Unsupported key type: %s (expected OKP)", key.kty);
/* 337 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 342 */       String json = String.format("{\"kty\":\"OKP\",\"crv\":\"%s\",\"x\":\"%s\",\"kid\":\"%s\",\"alg\":\"EdDSA\"}", new Object[] { key.crv, key.x, key.kid });
/*     */ 
/*     */ 
/*     */       
/* 346 */       return JWK.parse(json);
/* 347 */     } catch (Exception e) {
/* 348 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to parse Ed25519 key");
/* 349 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateJwksCache() {
/* 357 */     this.jwksFetchLock.lock();
/*     */     try {
/* 359 */       this.cachedJwkSet = null;
/* 360 */       this.jwksCacheExpiry = 0L;
/* 361 */       this.pendingFetch = null;
/*     */     } finally {
/* 363 */       this.jwksFetchLock.unlock();
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
/*     */   @Nullable
/*     */   public IdentityTokenClaims validateIdentityToken(@Nonnull String identityToken) {
/* 377 */     if (identityToken.isEmpty()) {
/* 378 */       LOGGER.at(Level.WARNING).log("Identity token is empty");
/* 379 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 384 */       SignedJWT signedJWT = SignedJWT.parse(identityToken);
/*     */ 
/*     */       
/* 387 */       JWSAlgorithm algorithm = signedJWT.getHeader().getAlgorithm();
/* 388 */       if (!SUPPORTED_ALGORITHM.equals(algorithm)) {
/* 389 */         LOGGER.at(Level.WARNING).log("Unsupported identity token algorithm: %s (expected EdDSA)", algorithm);
/* 390 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 394 */       if (!verifySignatureWithRetry(signedJWT)) {
/* 395 */         LOGGER.at(Level.WARNING).log("Identity token signature verification failed");
/* 396 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 400 */       JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
/* 401 */       IdentityTokenClaims claims = new IdentityTokenClaims();
/* 402 */       claims.issuer = claimsSet.getIssuer();
/* 403 */       claims.subject = claimsSet.getSubject();
/* 404 */       claims.username = claimsSet.getStringClaim("username");
/* 405 */       claims.issuedAt = (claimsSet.getIssueTime() != null) ? Long.valueOf(claimsSet.getIssueTime().toInstant().getEpochSecond()) : null;
/* 406 */       claims.expiresAt = (claimsSet.getExpirationTime() != null) ? Long.valueOf(claimsSet.getExpirationTime().toInstant().getEpochSecond()) : null;
/* 407 */       claims.notBefore = (claimsSet.getNotBeforeTime() != null) ? Long.valueOf(claimsSet.getNotBeforeTime().toInstant().getEpochSecond()) : null;
/* 408 */       claims.scope = claimsSet.getStringClaim("scope");
/*     */ 
/*     */       
/* 411 */       if (!this.expectedIssuer.equals(claims.issuer)) {
/* 412 */         LOGGER.at(Level.WARNING).log("Invalid identity token issuer: expected %s, got %s", this.expectedIssuer, claims.issuer);
/* 413 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 417 */       long nowSeconds = Instant.now().getEpochSecond();
/* 418 */       long clockSkewSeconds = 60L;
/*     */ 
/*     */       
/* 421 */       if (claims.expiresAt == null) {
/* 422 */         LOGGER.at(Level.WARNING).log("Identity token missing expiration claim");
/* 423 */         return null;
/*     */       } 
/*     */       
/* 426 */       if (nowSeconds >= claims.expiresAt.longValue() + clockSkewSeconds) {
/* 427 */         LOGGER.at(Level.WARNING).log("Identity token expired (exp: %d, now: %d)", claims.expiresAt, nowSeconds);
/* 428 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 432 */       if (claims.notBefore != null && nowSeconds < claims.notBefore.longValue() - clockSkewSeconds) {
/* 433 */         LOGGER.at(Level.WARNING).log("Identity token not yet valid (nbf: %d, now: %d)", claims.notBefore, nowSeconds);
/* 434 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 438 */       if (claims.issuedAt != null && claims.issuedAt.longValue() > nowSeconds + clockSkewSeconds) {
/* 439 */         LOGGER.at(Level.WARNING).log("Identity token issued in the future (iat: %d, now: %d)", claims.issuedAt, nowSeconds);
/* 440 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 444 */       if (claims.getSubjectAsUUID() == null) {
/* 445 */         LOGGER.at(Level.WARNING).log("Identity token has invalid or missing subject UUID");
/* 446 */         return null;
/*     */       } 
/*     */       
/* 449 */       LOGGER.at(Level.INFO).log("Identity token validated successfully for user %s (UUID: %s)", claims.username, claims.subject);
/* 450 */       return claims;
/*     */     }
/* 452 */     catch (ParseException e) {
/* 453 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to parse identity token");
/* 454 */       return null;
/* 455 */     } catch (Exception e) {
/* 456 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Identity token validation error");
/* 457 */       return null;
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
/*     */   @Nullable
/*     */   public SessionTokenClaims validateSessionToken(@Nonnull String sessionToken) {
/* 471 */     if (sessionToken.isEmpty()) {
/* 472 */       LOGGER.at(Level.WARNING).log("Session token is empty");
/* 473 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 478 */       SignedJWT signedJWT = SignedJWT.parse(sessionToken);
/*     */ 
/*     */       
/* 481 */       JWSAlgorithm algorithm = signedJWT.getHeader().getAlgorithm();
/* 482 */       if (!SUPPORTED_ALGORITHM.equals(algorithm)) {
/* 483 */         LOGGER.at(Level.WARNING).log("Unsupported session token algorithm: %s (expected EdDSA)", algorithm);
/* 484 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 488 */       if (!verifySignatureWithRetry(signedJWT)) {
/* 489 */         LOGGER.at(Level.WARNING).log("Session token signature verification failed");
/* 490 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 494 */       JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
/* 495 */       SessionTokenClaims claims = new SessionTokenClaims();
/* 496 */       claims.issuer = claimsSet.getIssuer();
/* 497 */       claims.subject = claimsSet.getSubject();
/* 498 */       claims.issuedAt = (claimsSet.getIssueTime() != null) ? Long.valueOf(claimsSet.getIssueTime().toInstant().getEpochSecond()) : null;
/* 499 */       claims.expiresAt = (claimsSet.getExpirationTime() != null) ? Long.valueOf(claimsSet.getExpirationTime().toInstant().getEpochSecond()) : null;
/* 500 */       claims.notBefore = (claimsSet.getNotBeforeTime() != null) ? Long.valueOf(claimsSet.getNotBeforeTime().toInstant().getEpochSecond()) : null;
/*     */ 
/*     */       
/* 503 */       if (!this.expectedIssuer.equals(claims.issuer)) {
/* 504 */         LOGGER.at(Level.WARNING).log("Invalid session token issuer: expected %s, got %s", this.expectedIssuer, claims.issuer);
/* 505 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 509 */       long nowSeconds = Instant.now().getEpochSecond();
/* 510 */       long clockSkewSeconds = 60L;
/*     */ 
/*     */       
/* 513 */       if (claims.expiresAt == null) {
/* 514 */         LOGGER.at(Level.WARNING).log("Session token missing expiration claim");
/* 515 */         return null;
/*     */       } 
/*     */       
/* 518 */       if (nowSeconds >= claims.expiresAt.longValue() + clockSkewSeconds) {
/* 519 */         LOGGER.at(Level.WARNING).log("Session token expired (exp: %d, now: %d)", claims.expiresAt, nowSeconds);
/* 520 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 524 */       if (claims.notBefore != null && nowSeconds < claims.notBefore.longValue() - clockSkewSeconds) {
/* 525 */         LOGGER.at(Level.WARNING).log("Session token not yet valid (nbf: %d, now: %d)", claims.notBefore, nowSeconds);
/* 526 */         return null;
/*     */       } 
/*     */       
/* 529 */       LOGGER.at(Level.INFO).log("Session token validated successfully");
/* 530 */       return claims;
/*     */     }
/* 532 */     catch (ParseException e) {
/* 533 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to parse session token");
/* 534 */       return null;
/* 535 */     } catch (Exception e) {
/* 536 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Session token validation error");
/* 537 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SessionTokenClaims
/*     */   {
/*     */     public String issuer;
/*     */     
/*     */     public String subject;
/*     */     
/*     */     public Long issuedAt;
/*     */     
/*     */     public Long expiresAt;
/*     */     
/*     */     public Long notBefore;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IdentityTokenClaims
/*     */   {
/*     */     public String issuer;
/*     */     
/*     */     public String subject;
/*     */     
/*     */     public String username;
/*     */     
/*     */     public Long issuedAt;
/*     */     public Long expiresAt;
/*     */     public Long notBefore;
/*     */     public String scope;
/*     */     
/*     */     @Nullable
/*     */     public UUID getSubjectAsUUID() {
/* 571 */       if (this.subject == null) return null; 
/*     */       try {
/* 573 */         return UUID.fromString(this.subject);
/* 574 */       } catch (IllegalArgumentException e) {
/* 575 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String[] getScopes() {
/* 584 */       if (this.scope == null || this.scope.isEmpty()) return new String[0]; 
/* 585 */       return this.scope.split(" ");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasScope(@Nonnull String targetScope) {
/* 592 */       for (String s : getScopes()) {
/* 593 */         if (s.equals(targetScope)) return true; 
/*     */       } 
/* 595 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class JWTClaims
/*     */   {
/*     */     public String issuer;
/*     */     
/*     */     public String audience;
/*     */     
/*     */     public String subject;
/*     */     
/*     */     public String username;
/*     */     
/*     */     public String ipAddress;
/*     */     public Long issuedAt;
/*     */     public Long expiresAt;
/*     */     public Long notBefore;
/*     */     public String certificateFingerprint;
/*     */     
/*     */     @Nullable
/*     */     public UUID getSubjectAsUUID() {
/* 618 */       if (this.subject == null) return null; 
/*     */       try {
/* 620 */         return UUID.fromString(this.subject);
/* 621 */       } catch (IllegalArgumentException e) {
/* 622 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\JWTValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */