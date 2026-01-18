/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.time.Instant;
/*     */ import java.util.List;
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
/*     */ public final class VerifiedJwt
/*     */ {
/*     */   private final RawJwt rawJwt;
/*     */   
/*     */   VerifiedJwt(RawJwt rawJwt) {
/*  41 */     this.rawJwt = rawJwt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeHeader() throws JwtInvalidException {
/*  48 */     return this.rawJwt.getTypeHeader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTypeHeader() {
/*  55 */     return this.rawJwt.hasTypeHeader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssuer() throws JwtInvalidException {
/*  63 */     return this.rawJwt.getIssuer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIssuer() {
/*  70 */     return this.rawJwt.hasIssuer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubject() throws JwtInvalidException {
/*  78 */     return this.rawJwt.getSubject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubject() {
/*  85 */     return this.rawJwt.hasSubject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getAudiences() throws JwtInvalidException {
/*  93 */     return this.rawJwt.getAudiences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAudiences() {
/* 100 */     return this.rawJwt.hasAudiences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJwtId() throws JwtInvalidException {
/* 108 */     return this.rawJwt.getJwtId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasJwtId() {
/* 115 */     return this.rawJwt.hasJwtId();
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
/*     */   public Instant getExpiration() throws JwtInvalidException {
/* 128 */     return this.rawJwt.getExpiration();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasExpiration() {
/* 135 */     return this.rawJwt.hasExpiration();
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
/*     */   public Instant getNotBefore() throws JwtInvalidException {
/* 148 */     return this.rawJwt.getNotBefore();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNotBefore() {
/* 155 */     return this.rawJwt.hasNotBefore();
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
/*     */   public Instant getIssuedAt() throws JwtInvalidException {
/* 167 */     return this.rawJwt.getIssuedAt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIssuedAt() {
/* 174 */     return this.rawJwt.hasIssuedAt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getBooleanClaim(String name) throws JwtInvalidException {
/* 182 */     return this.rawJwt.getBooleanClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getNumberClaim(String name) throws JwtInvalidException {
/* 190 */     return this.rawJwt.getNumberClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringClaim(String name) throws JwtInvalidException {
/* 198 */     return this.rawJwt.getStringClaim(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNullClaim(String name) {
/* 203 */     return this.rawJwt.isNullClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJsonObjectClaim(String name) throws JwtInvalidException {
/* 211 */     return this.rawJwt.getJsonObjectClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJsonArrayClaim(String name) throws JwtInvalidException {
/* 219 */     return this.rawJwt.getJsonArrayClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBooleanClaim(String name) {
/* 226 */     return this.rawJwt.hasBooleanClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNumberClaim(String name) {
/* 233 */     return this.rawJwt.hasNumberClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStringClaim(String name) {
/* 240 */     return this.rawJwt.hasStringClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasJsonObjectClaim(String name) {
/* 247 */     return this.rawJwt.hasJsonObjectClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasJsonArrayClaim(String name) {
/* 254 */     return this.rawJwt.hasJsonArrayClaim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> customClaimNames() {
/* 261 */     return this.rawJwt.customClaimNames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 270 */     return "verified{" + this.rawJwt + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\VerifiedJwt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */