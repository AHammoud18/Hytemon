/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.time.Clock;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
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
/*     */ @Immutable
/*     */ public final class JwtValidator
/*     */ {
/*  31 */   private static final Duration MAX_CLOCK_SKEW = Duration.ofMinutes(10L);
/*     */   
/*     */   private final Optional<String> expectedTypeHeader;
/*     */   
/*     */   private final boolean ignoreTypeHeader;
/*     */   
/*     */   private final Optional<String> expectedIssuer;
/*     */   
/*     */   private final boolean ignoreIssuer;
/*     */   private final Optional<String> expectedAudience;
/*     */   private final boolean ignoreAudiences;
/*     */   private final boolean allowMissingExpiration;
/*     */   private final boolean expectIssuedInThePast;
/*     */   private final Clock clock;
/*     */   private final Duration clockSkew;
/*     */   
/*     */   private JwtValidator(Builder builder) {
/*  48 */     this.expectedTypeHeader = builder.expectedTypeHeader;
/*  49 */     this.ignoreTypeHeader = builder.ignoreTypeHeader;
/*  50 */     this.expectedIssuer = builder.expectedIssuer;
/*  51 */     this.ignoreIssuer = builder.ignoreIssuer;
/*  52 */     this.expectedAudience = builder.expectedAudience;
/*  53 */     this.ignoreAudiences = builder.ignoreAudiences;
/*  54 */     this.allowMissingExpiration = builder.allowMissingExpiration;
/*  55 */     this.expectIssuedInThePast = builder.expectIssuedInThePast;
/*  56 */     this.clock = builder.clock;
/*  57 */     this.clockSkew = builder.clockSkew;
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
/*     */   public static Builder newBuilder() {
/*  71 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private Optional<String> expectedTypeHeader;
/*     */     private boolean ignoreTypeHeader;
/*     */     private Optional<String> expectedIssuer;
/*     */     private boolean ignoreIssuer;
/*     */     private Optional<String> expectedAudience;
/*     */     private boolean ignoreAudiences;
/*     */     private boolean allowMissingExpiration;
/*     */     private boolean expectIssuedInThePast;
/*  84 */     private Clock clock = Clock.systemUTC();
/*  85 */     private Duration clockSkew = Duration.ZERO;
/*     */     
/*     */     private Builder() {
/*  88 */       this.expectedTypeHeader = Optional.empty();
/*  89 */       this.ignoreTypeHeader = false;
/*  90 */       this.expectedIssuer = Optional.empty();
/*  91 */       this.ignoreIssuer = false;
/*  92 */       this.expectedAudience = Optional.empty();
/*  93 */       this.ignoreAudiences = false;
/*  94 */       this.allowMissingExpiration = false;
/*  95 */       this.expectIssuedInThePast = false;
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public Builder expectTypeHeader(String value) {
/* 110 */       if (value == null) {
/* 111 */         throw new NullPointerException("typ header cannot be null");
/*     */       }
/* 113 */       this.expectedTypeHeader = Optional.of(value);
/* 114 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder ignoreTypeHeader() {
/* 120 */       this.ignoreTypeHeader = true;
/* 121 */       return this;
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public Builder expectIssuer(String value) {
/* 136 */       if (value == null) {
/* 137 */         throw new NullPointerException("issuer cannot be null");
/*     */       }
/* 139 */       this.expectedIssuer = Optional.of(value);
/* 140 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder ignoreIssuer() {
/* 146 */       this.ignoreIssuer = true;
/* 147 */       return this;
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public Builder expectAudience(String value) {
/* 162 */       if (value == null) {
/* 163 */         throw new NullPointerException("audience cannot be null");
/*     */       }
/* 165 */       this.expectedAudience = Optional.of(value);
/* 166 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder ignoreAudiences() {
/* 172 */       this.ignoreAudiences = true;
/* 173 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder expectIssuedInThePast() {
/* 179 */       this.expectIssuedInThePast = true;
/* 180 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setClock(Clock clock) {
/* 186 */       if (clock == null) {
/* 187 */         throw new NullPointerException("clock cannot be null");
/*     */       }
/* 189 */       this.clock = clock;
/* 190 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setClockSkew(Duration clockSkew) {
/* 202 */       if (clockSkew.compareTo(JwtValidator.MAX_CLOCK_SKEW) > 0) {
/* 203 */         throw new IllegalArgumentException("Clock skew too large, max is 10 minutes");
/*     */       }
/* 205 */       this.clockSkew = clockSkew;
/* 206 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder allowMissingExpiration() {
/* 217 */       this.allowMissingExpiration = true;
/* 218 */       return this;
/*     */     }
/*     */     
/*     */     public JwtValidator build() {
/* 222 */       if (this.ignoreTypeHeader && this.expectedTypeHeader.isPresent()) {
/* 223 */         throw new IllegalArgumentException("ignoreTypeHeader() and expectedTypeHeader() cannot be used together.");
/*     */       }
/*     */       
/* 226 */       if (this.ignoreIssuer && this.expectedIssuer.isPresent()) {
/* 227 */         throw new IllegalArgumentException("ignoreIssuer() and expectedIssuer() cannot be used together.");
/*     */       }
/*     */       
/* 230 */       if (this.ignoreAudiences && this.expectedAudience.isPresent()) {
/* 231 */         throw new IllegalArgumentException("ignoreAudiences() and expectedAudience() cannot be used together.");
/*     */       }
/*     */       
/* 234 */       return new JwtValidator(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void validateTypeHeader(RawJwt target) throws JwtInvalidException {
/* 239 */     if (this.expectedTypeHeader.isPresent()) {
/* 240 */       if (!target.hasTypeHeader())
/* 241 */         throw new JwtInvalidException(
/* 242 */             String.format("invalid JWT; missing expected type header %s.", new Object[] {
/* 243 */                 this.expectedTypeHeader.get()
/*     */               })); 
/* 245 */       if (!target.getTypeHeader().equals(this.expectedTypeHeader.get())) {
/* 246 */         throw new JwtInvalidException(
/* 247 */             String.format("invalid JWT; expected type header %s, but got %s", new Object[] {
/*     */                 
/* 249 */                 this.expectedTypeHeader.get(), target.getTypeHeader()
/*     */               }));
/*     */       }
/* 252 */     } else if (target.hasTypeHeader() && !this.ignoreTypeHeader) {
/* 253 */       throw new JwtInvalidException("invalid JWT; token has type header set, but validator not.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateIssuer(RawJwt target) throws JwtInvalidException {
/* 259 */     if (this.expectedIssuer.isPresent()) {
/* 260 */       if (!target.hasIssuer()) {
/* 261 */         throw new JwtInvalidException(
/* 262 */             String.format("invalid JWT; missing expected issuer %s.", new Object[] { this.expectedIssuer.get() }));
/*     */       }
/* 264 */       if (!target.getIssuer().equals(this.expectedIssuer.get())) {
/* 265 */         throw new JwtInvalidException(
/* 266 */             String.format("invalid JWT; expected issuer %s, but got %s", new Object[] {
/*     */                 
/* 268 */                 this.expectedIssuer.get(), target.getIssuer()
/*     */               }));
/*     */       }
/* 271 */     } else if (target.hasIssuer() && !this.ignoreIssuer) {
/* 272 */       throw new JwtInvalidException("invalid JWT; token has issuer set, but validator not.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateAudiences(RawJwt target) throws JwtInvalidException {
/* 278 */     if (this.expectedAudience.isPresent()) {
/* 279 */       if (!target.hasAudiences() || !target.getAudiences().contains(this.expectedAudience.get())) {
/* 280 */         throw new JwtInvalidException(
/* 281 */             String.format("invalid JWT; missing expected audience %s.", new Object[] {
/* 282 */                 this.expectedAudience.get()
/*     */               }));
/*     */       }
/* 285 */     } else if (target.hasAudiences() && !this.ignoreAudiences) {
/* 286 */       throw new JwtInvalidException("invalid JWT; token has audience set, but validator not.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   VerifiedJwt validate(RawJwt target) throws JwtInvalidException {
/* 296 */     validateTimestampClaims(target);
/* 297 */     validateTypeHeader(target);
/* 298 */     validateIssuer(target);
/* 299 */     validateAudiences(target);
/* 300 */     return new VerifiedJwt(target);
/*     */   }
/*     */   
/*     */   private void validateTimestampClaims(RawJwt target) throws JwtInvalidException {
/* 304 */     Instant now = this.clock.instant();
/*     */     
/* 306 */     if (!target.hasExpiration() && !this.allowMissingExpiration) {
/* 307 */       throw new JwtInvalidException("token does not have an expiration set");
/*     */     }
/*     */ 
/*     */     
/* 311 */     if (target.hasExpiration() && !target.getExpiration().isAfter(now.minus(this.clockSkew))) {
/* 312 */       throw new JwtInvalidException("token has expired since " + target.getExpiration());
/*     */     }
/*     */ 
/*     */     
/* 316 */     if (target.hasNotBefore() && target.getNotBefore().isAfter(now.plus(this.clockSkew))) {
/* 317 */       throw new JwtInvalidException("token cannot be used before " + target.getNotBefore());
/*     */     }
/*     */ 
/*     */     
/* 321 */     if (this.expectIssuedInThePast) {
/* 322 */       if (!target.hasIssuedAt()) {
/* 323 */         throw new JwtInvalidException("token does not have an iat claim");
/*     */       }
/* 325 */       if (target.getIssuedAt().isAfter(now.plus(this.clockSkew))) {
/* 326 */         throw new JwtInvalidException("token has a invalid iat claim in the future: " + target
/* 327 */             .getIssuedAt());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 338 */     List<String> items = new ArrayList<>();
/* 339 */     if (this.expectedTypeHeader.isPresent()) {
/* 340 */       items.add("expectedTypeHeader=" + (String)this.expectedTypeHeader.get());
/*     */     }
/* 342 */     if (this.ignoreTypeHeader) {
/* 343 */       items.add("ignoreTypeHeader");
/*     */     }
/* 345 */     if (this.expectedIssuer.isPresent()) {
/* 346 */       items.add("expectedIssuer=" + (String)this.expectedIssuer.get());
/*     */     }
/* 348 */     if (this.ignoreIssuer) {
/* 349 */       items.add("ignoreIssuer");
/*     */     }
/* 351 */     if (this.expectedAudience.isPresent()) {
/* 352 */       items.add("expectedAudience=" + (String)this.expectedAudience.get());
/*     */     }
/* 354 */     if (this.ignoreAudiences) {
/* 355 */       items.add("ignoreAudiences");
/*     */     }
/* 357 */     if (this.allowMissingExpiration) {
/* 358 */       items.add("allowMissingExpiration");
/*     */     }
/* 360 */     if (this.expectIssuedInThePast) {
/* 361 */       items.add("expectIssuedInThePast");
/*     */     }
/* 363 */     if (!this.clockSkew.isZero()) {
/* 364 */       items.add("clockSkew=" + this.clockSkew);
/*     */     }
/* 366 */     StringBuilder b = new StringBuilder();
/* 367 */     b.append("JwtValidator{");
/* 368 */     String currentSeparator = "";
/* 369 */     for (String i : items) {
/* 370 */       b.append(currentSeparator);
/* 371 */       b.append(i);
/* 372 */       currentSeparator = ",";
/*     */     } 
/* 374 */     b.append("}");
/* 375 */     return b.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */