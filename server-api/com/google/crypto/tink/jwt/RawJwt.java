/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.jwt.internal.JsonUtil;
/*     */ import com.google.crypto.tink.jwt.internal.JwtNames;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ @Immutable
/*     */ public final class RawJwt
/*     */ {
/*     */   private static final long MAX_TIMESTAMP_VALUE = 253402300799L;
/*     */   private final JsonObject payload;
/*     */   private final Optional<String> typeHeader;
/*     */   
/*     */   private RawJwt(Builder builder) {
/*  57 */     if (!builder.payload.has("exp") && !builder.withoutExpiration) {
/*  58 */       throw new IllegalArgumentException("neither setExpiration() nor withoutExpiration() was called");
/*     */     }
/*     */     
/*  61 */     if (builder.payload.has("exp") && builder.withoutExpiration) {
/*  62 */       throw new IllegalArgumentException("setExpiration() and withoutExpiration() must not be called together");
/*     */     }
/*     */     
/*  65 */     this.typeHeader = builder.typeHeader;
/*  66 */     this.payload = builder.payload.deepCopy();
/*     */   }
/*     */   
/*     */   private RawJwt(Optional<String> typeHeader, String jsonPayload) throws JwtInvalidException {
/*  70 */     this.typeHeader = typeHeader;
/*  71 */     this.payload = JsonUtil.parseJson(jsonPayload);
/*  72 */     validateStringClaim("iss");
/*  73 */     validateStringClaim("sub");
/*  74 */     validateStringClaim("jti");
/*  75 */     validateTimestampClaim("exp");
/*  76 */     validateTimestampClaim("nbf");
/*  77 */     validateTimestampClaim("iat");
/*  78 */     validateAudienceClaim();
/*     */   }
/*     */   
/*     */   private void validateStringClaim(String name) throws JwtInvalidException {
/*  82 */     if (!this.payload.has(name)) {
/*     */       return;
/*     */     }
/*  85 */     if (!this.payload.get(name).isJsonPrimitive() || 
/*  86 */       !this.payload.get(name).getAsJsonPrimitive().isString()) {
/*  87 */       throw new JwtInvalidException("invalid JWT payload: claim " + name + " is not a string.");
/*     */     }
/*     */   }
/*     */   
/*     */   private void validateTimestampClaim(String name) throws JwtInvalidException {
/*  92 */     if (!this.payload.has(name)) {
/*     */       return;
/*     */     }
/*  95 */     if (!this.payload.get(name).isJsonPrimitive() || 
/*  96 */       !this.payload.get(name).getAsJsonPrimitive().isNumber()) {
/*  97 */       throw new JwtInvalidException("invalid JWT payload: claim " + name + " is not a number.");
/*     */     }
/*  99 */     double timestamp = this.payload.get(name).getAsJsonPrimitive().getAsDouble();
/* 100 */     if (timestamp > 2.53402300799E11D || timestamp < 0.0D) {
/* 101 */       throw new JwtInvalidException("invalid JWT payload: claim " + name + " has an invalid timestamp");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateAudienceClaim() throws JwtInvalidException {
/* 107 */     if (!this.payload.has("aud")) {
/*     */       return;
/*     */     }
/* 110 */     if (this.payload.get("aud").isJsonPrimitive() && this.payload
/* 111 */       .get("aud").getAsJsonPrimitive().isString()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 117 */     List<String> audiences = getAudiences();
/* 118 */     if (audiences.size() < 1) {
/* 119 */       throw new JwtInvalidException("invalid JWT payload: claim aud is present but empty.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static RawJwt fromJsonPayload(Optional<String> typeHeader, String jsonPayload) throws JwtInvalidException {
/* 126 */     return new RawJwt(typeHeader, jsonPayload);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder newBuilder() {
/* 133 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 143 */     private Optional<String> typeHeader = Optional.empty();
/*     */     private boolean withoutExpiration = false;
/* 145 */     private final JsonObject payload = new JsonObject();
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
/*     */     public Builder setTypeHeader(String value) {
/* 157 */       this.typeHeader = Optional.of(value);
/* 158 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIssuer(String value) {
/* 168 */       if (!JsonUtil.isValidString(value)) {
/* 169 */         throw new IllegalArgumentException();
/*     */       }
/* 171 */       this.payload.add("iss", (JsonElement)new JsonPrimitive(value));
/* 172 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSubject(String value) {
/* 182 */       if (!JsonUtil.isValidString(value)) {
/* 183 */         throw new IllegalArgumentException();
/*     */       }
/* 185 */       this.payload.add("sub", (JsonElement)new JsonPrimitive(value));
/* 186 */       return this;
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
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAudience(String value) {
/* 199 */       if (this.payload.has("aud") && this.payload
/* 200 */         .get("aud").isJsonArray()) {
/* 201 */         throw new IllegalArgumentException("setAudience can't be used together with setAudiences or addAudience");
/*     */       }
/*     */       
/* 204 */       if (!JsonUtil.isValidString(value)) {
/* 205 */         throw new IllegalArgumentException("invalid string");
/*     */       }
/* 207 */       this.payload.add("aud", (JsonElement)new JsonPrimitive(value));
/* 208 */       return this;
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
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAudiences(List<String> values) {
/* 221 */       if (this.payload.has("aud") && 
/* 222 */         !this.payload.get("aud").isJsonArray()) {
/* 223 */         throw new IllegalArgumentException("setAudiences can't be used together with setAudience");
/*     */       }
/* 225 */       if (values.isEmpty()) {
/* 226 */         throw new IllegalArgumentException("audiences must not be empty");
/*     */       }
/* 228 */       JsonArray audiences = new JsonArray();
/* 229 */       for (String value : values) {
/* 230 */         if (!JsonUtil.isValidString(value)) {
/* 231 */           throw new IllegalArgumentException("invalid string");
/*     */         }
/* 233 */         audiences.add(value);
/*     */       } 
/* 235 */       this.payload.add("aud", (JsonElement)audiences);
/* 236 */       return this;
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
/*     */     public Builder addAudience(String value) {
/*     */       JsonArray audiences;
/* 249 */       if (!JsonUtil.isValidString(value)) {
/* 250 */         throw new IllegalArgumentException("invalid string");
/*     */       }
/*     */       
/* 253 */       if (this.payload.has("aud")) {
/* 254 */         JsonElement aud = this.payload.get("aud");
/* 255 */         if (!aud.isJsonArray()) {
/* 256 */           throw new IllegalArgumentException("addAudience can't be used together with setAudience");
/*     */         }
/*     */         
/* 259 */         audiences = aud.getAsJsonArray();
/*     */       } else {
/* 261 */         audiences = new JsonArray();
/*     */       } 
/* 263 */       audiences.add(value);
/* 264 */       this.payload.add("aud", (JsonElement)audiences);
/* 265 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setJwtId(String value) {
/* 275 */       if (!JsonUtil.isValidString(value)) {
/* 276 */         throw new IllegalArgumentException();
/*     */       }
/* 278 */       this.payload.add("jti", (JsonElement)new JsonPrimitive(value));
/* 279 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private void setTimestampClaim(String name, Instant value) {
/* 284 */       long timestamp = value.getEpochSecond();
/* 285 */       if (timestamp > 253402300799L || timestamp < 0L) {
/* 286 */         throw new IllegalArgumentException("timestamp of claim " + name + " is out of range");
/*     */       }
/*     */       
/* 289 */       this.payload.add(name, (JsonElement)new JsonPrimitive(Long.valueOf(timestamp)));
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
/*     */     public Builder setExpiration(Instant value) {
/* 304 */       setTimestampClaim("exp", value);
/* 305 */       return this;
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
/*     */     public Builder withoutExpiration() {
/* 317 */       this.withoutExpiration = true;
/* 318 */       return this;
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
/*     */     public Builder setNotBefore(Instant value) {
/* 333 */       setTimestampClaim("nbf", value);
/* 334 */       return this;
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
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIssuedAt(Instant value) {
/* 348 */       setTimestampClaim("iat", value);
/* 349 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addBooleanClaim(String name, boolean value) {
/* 355 */       JwtNames.validate(name);
/* 356 */       this.payload.add(name, (JsonElement)new JsonPrimitive(Boolean.valueOf(value)));
/* 357 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addNumberClaim(String name, long value) {
/* 363 */       JwtNames.validate(name);
/* 364 */       this.payload.add(name, (JsonElement)new JsonPrimitive(Long.valueOf(value)));
/* 365 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addNumberClaim(String name, double value) {
/* 371 */       JwtNames.validate(name);
/* 372 */       this.payload.add(name, (JsonElement)new JsonPrimitive(Double.valueOf(value)));
/* 373 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addStringClaim(String name, String value) {
/* 379 */       if (!JsonUtil.isValidString(value)) {
/* 380 */         throw new IllegalArgumentException();
/*     */       }
/* 382 */       JwtNames.validate(name);
/* 383 */       this.payload.add(name, (JsonElement)new JsonPrimitive(value));
/* 384 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addNullClaim(String name) {
/* 390 */       JwtNames.validate(name);
/* 391 */       this.payload.add(name, (JsonElement)JsonNull.INSTANCE);
/* 392 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addJsonObjectClaim(String name, String encodedJsonObject) throws JwtInvalidException {
/* 399 */       JwtNames.validate(name);
/* 400 */       this.payload.add(name, (JsonElement)JsonUtil.parseJson(encodedJsonObject));
/* 401 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addJsonArrayClaim(String name, String encodedJsonArray) throws JwtInvalidException {
/* 408 */       JwtNames.validate(name);
/* 409 */       this.payload.add(name, (JsonElement)JsonUtil.parseJsonArray(encodedJsonArray));
/* 410 */       return this;
/*     */     }
/*     */     
/*     */     public RawJwt build() {
/* 414 */       return new RawJwt(this);
/*     */     }
/*     */     private Builder() {} }
/*     */   
/*     */   public String getJsonPayload() {
/* 419 */     return this.payload.toString();
/*     */   }
/*     */   
/*     */   boolean hasBooleanClaim(String name) {
/* 423 */     JwtNames.validate(name);
/* 424 */     return (this.payload.has(name) && this.payload
/* 425 */       .get(name).isJsonPrimitive() && this.payload
/* 426 */       .get(name).getAsJsonPrimitive().isBoolean());
/*     */   }
/*     */   
/*     */   Boolean getBooleanClaim(String name) throws JwtInvalidException {
/* 430 */     JwtNames.validate(name);
/* 431 */     if (!this.payload.has(name)) {
/* 432 */       throw new JwtInvalidException("claim " + name + " does not exist");
/*     */     }
/* 434 */     if (!this.payload.get(name).isJsonPrimitive() || 
/* 435 */       !this.payload.get(name).getAsJsonPrimitive().isBoolean()) {
/* 436 */       throw new JwtInvalidException("claim " + name + " is not a boolean");
/*     */     }
/* 438 */     return Boolean.valueOf(this.payload.get(name).getAsBoolean());
/*     */   }
/*     */   
/*     */   boolean hasNumberClaim(String name) {
/* 442 */     JwtNames.validate(name);
/* 443 */     return (this.payload.has(name) && this.payload
/* 444 */       .get(name).isJsonPrimitive() && this.payload
/* 445 */       .get(name).getAsJsonPrimitive().isNumber());
/*     */   }
/*     */   
/*     */   Double getNumberClaim(String name) throws JwtInvalidException {
/* 449 */     JwtNames.validate(name);
/* 450 */     if (!this.payload.has(name)) {
/* 451 */       throw new JwtInvalidException("claim " + name + " does not exist");
/*     */     }
/* 453 */     if (!this.payload.get(name).isJsonPrimitive() || 
/* 454 */       !this.payload.get(name).getAsJsonPrimitive().isNumber()) {
/* 455 */       throw new JwtInvalidException("claim " + name + " is not a number");
/*     */     }
/* 457 */     return Double.valueOf(this.payload.get(name).getAsDouble());
/*     */   }
/*     */   
/*     */   boolean hasStringClaim(String name) {
/* 461 */     JwtNames.validate(name);
/* 462 */     return (this.payload.has(name) && this.payload
/* 463 */       .get(name).isJsonPrimitive() && this.payload
/* 464 */       .get(name).getAsJsonPrimitive().isString());
/*     */   }
/*     */   
/*     */   String getStringClaim(String name) throws JwtInvalidException {
/* 468 */     JwtNames.validate(name);
/* 469 */     return getStringClaimInternal(name);
/*     */   }
/*     */   
/*     */   private String getStringClaimInternal(String name) throws JwtInvalidException {
/* 473 */     if (!this.payload.has(name)) {
/* 474 */       throw new JwtInvalidException("claim " + name + " does not exist");
/*     */     }
/* 476 */     if (!this.payload.get(name).isJsonPrimitive() || 
/* 477 */       !this.payload.get(name).getAsJsonPrimitive().isString()) {
/* 478 */       throw new JwtInvalidException("claim " + name + " is not a string");
/*     */     }
/* 480 */     return this.payload.get(name).getAsString();
/*     */   }
/*     */   
/*     */   boolean isNullClaim(String name) {
/* 484 */     JwtNames.validate(name);
/*     */     try {
/* 486 */       return JsonNull.INSTANCE.equals(this.payload.get(name));
/* 487 */     } catch (JsonParseException ex) {
/* 488 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean hasJsonObjectClaim(String name) {
/* 493 */     JwtNames.validate(name);
/* 494 */     return (this.payload.has(name) && this.payload.get(name).isJsonObject());
/*     */   }
/*     */   
/*     */   String getJsonObjectClaim(String name) throws JwtInvalidException {
/* 498 */     JwtNames.validate(name);
/* 499 */     if (!this.payload.has(name)) {
/* 500 */       throw new JwtInvalidException("claim " + name + " does not exist");
/*     */     }
/* 502 */     if (!this.payload.get(name).isJsonObject()) {
/* 503 */       throw new JwtInvalidException("claim " + name + " is not a JSON object");
/*     */     }
/* 505 */     return this.payload.get(name).getAsJsonObject().toString();
/*     */   }
/*     */   
/*     */   boolean hasJsonArrayClaim(String name) {
/* 509 */     JwtNames.validate(name);
/* 510 */     return (this.payload.has(name) && this.payload.get(name).isJsonArray());
/*     */   }
/*     */   
/*     */   String getJsonArrayClaim(String name) throws JwtInvalidException {
/* 514 */     JwtNames.validate(name);
/* 515 */     if (!this.payload.has(name)) {
/* 516 */       throw new JwtInvalidException("claim " + name + " does not exist");
/*     */     }
/* 518 */     if (!this.payload.get(name).isJsonArray()) {
/* 519 */       throw new JwtInvalidException("claim " + name + " is not a JSON array");
/*     */     }
/* 521 */     return this.payload.get(name).getAsJsonArray().toString();
/*     */   }
/*     */   
/*     */   boolean hasTypeHeader() {
/* 525 */     return this.typeHeader.isPresent();
/*     */   }
/*     */   
/*     */   String getTypeHeader() throws JwtInvalidException {
/* 529 */     if (!this.typeHeader.isPresent()) {
/* 530 */       throw new JwtInvalidException("type header is not set");
/*     */     }
/* 532 */     return this.typeHeader.get();
/*     */   }
/*     */   
/*     */   boolean hasIssuer() {
/* 536 */     return this.payload.has("iss");
/*     */   }
/*     */   
/*     */   String getIssuer() throws JwtInvalidException {
/* 540 */     return getStringClaimInternal("iss");
/*     */   }
/*     */   
/*     */   boolean hasSubject() {
/* 544 */     return this.payload.has("sub");
/*     */   }
/*     */   
/*     */   String getSubject() throws JwtInvalidException {
/* 548 */     return getStringClaimInternal("sub");
/*     */   }
/*     */   
/*     */   boolean hasJwtId() {
/* 552 */     return this.payload.has("jti");
/*     */   }
/*     */   
/*     */   String getJwtId() throws JwtInvalidException {
/* 556 */     return getStringClaimInternal("jti");
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasAudiences() {
/* 561 */     return this.payload.has("aud");
/*     */   }
/*     */   
/*     */   List<String> getAudiences() throws JwtInvalidException {
/* 565 */     if (!hasAudiences()) {
/* 566 */       throw new JwtInvalidException("claim aud does not exist");
/*     */     }
/* 568 */     JsonElement aud = this.payload.get("aud");
/* 569 */     if (aud.isJsonPrimitive()) {
/* 570 */       if (!aud.getAsJsonPrimitive().isString()) {
/* 571 */         throw new JwtInvalidException(
/* 572 */             String.format("invalid audience: got %s; want a string", new Object[] { aud }));
/*     */       }
/* 574 */       return Collections.unmodifiableList(Arrays.asList(new String[] { aud.getAsString() }));
/*     */     } 
/* 576 */     if (!aud.isJsonArray()) {
/* 577 */       throw new JwtInvalidException("claim aud is not a string or a JSON array");
/*     */     }
/*     */     
/* 580 */     JsonArray audiences = aud.getAsJsonArray();
/* 581 */     List<String> result = new ArrayList<>(audiences.size());
/* 582 */     for (int i = 0; i < audiences.size(); i++) {
/* 583 */       if (!audiences.get(i).isJsonPrimitive() || 
/* 584 */         !audiences.get(i).getAsJsonPrimitive().isString()) {
/* 585 */         throw new JwtInvalidException(
/* 586 */             String.format("invalid audience: got %s; want a string", new Object[] { audiences.get(i) }));
/*     */       }
/* 588 */       String audience = audiences.get(i).getAsString();
/* 589 */       result.add(audience);
/*     */     } 
/*     */     
/* 592 */     return Collections.unmodifiableList(result);
/*     */   }
/*     */   
/*     */   private Instant getInstant(String name) throws JwtInvalidException {
/* 596 */     if (!this.payload.has(name)) {
/* 597 */       throw new JwtInvalidException("claim " + name + " does not exist");
/*     */     }
/* 599 */     if (!this.payload.get(name).isJsonPrimitive() || 
/* 600 */       !this.payload.get(name).getAsJsonPrimitive().isNumber()) {
/* 601 */       throw new JwtInvalidException("claim " + name + " is not a timestamp");
/*     */     }
/*     */     try {
/* 604 */       double millis = this.payload.get(name).getAsJsonPrimitive().getAsDouble() * 1000.0D;
/* 605 */       return Instant.ofEpochMilli((long)millis);
/* 606 */     } catch (NumberFormatException ex) {
/* 607 */       throw new JwtInvalidException("claim " + name + " is not a timestamp: " + ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean hasExpiration() {
/* 612 */     return this.payload.has("exp");
/*     */   }
/*     */   
/*     */   Instant getExpiration() throws JwtInvalidException {
/* 616 */     return getInstant("exp");
/*     */   }
/*     */   
/*     */   boolean hasNotBefore() {
/* 620 */     return this.payload.has("nbf");
/*     */   }
/*     */   
/*     */   Instant getNotBefore() throws JwtInvalidException {
/* 624 */     return getInstant("nbf");
/*     */   }
/*     */   
/*     */   boolean hasIssuedAt() {
/* 628 */     return this.payload.has("iat");
/*     */   }
/*     */   
/*     */   Instant getIssuedAt() throws JwtInvalidException {
/* 632 */     return getInstant("iat");
/*     */   }
/*     */ 
/*     */   
/*     */   Set<String> customClaimNames() {
/* 637 */     HashSet<String> names = new HashSet<>();
/* 638 */     for (String name : this.payload.keySet()) {
/* 639 */       if (!JwtNames.isRegisteredName(name)) {
/* 640 */         names.add(name);
/*     */       }
/*     */     } 
/* 643 */     return Collections.unmodifiableSet(names);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 652 */     JsonObject header = new JsonObject();
/* 653 */     if (this.typeHeader.isPresent()) {
/* 654 */       header.add("typ", (JsonElement)new JsonPrimitive(this.typeHeader.get()));
/*     */     }
/* 656 */     return header + "." + this.payload;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\RawJwt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */