/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ public final class JwtRsaSsaPssPrivateKey
/*     */   extends JwtSignaturePrivateKey
/*     */ {
/*     */   private final JwtRsaSsaPssPublicKey publicKey;
/*     */   private final RsaSsaPssPrivateKey rsaSsaPssPrivateKey;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  39 */     private Optional<JwtRsaSsaPssPublicKey> publicKey = Optional.empty();
/*  40 */     private Optional<SecretBigInteger> d = Optional.empty();
/*  41 */     private Optional<SecretBigInteger> p = Optional.empty();
/*  42 */     private Optional<SecretBigInteger> q = Optional.empty();
/*  43 */     private Optional<SecretBigInteger> dP = Optional.empty();
/*  44 */     private Optional<SecretBigInteger> dQ = Optional.empty();
/*  45 */     private Optional<SecretBigInteger> qInv = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicKey(JwtRsaSsaPssPublicKey publicKey) {
/*  56 */       this.publicKey = Optional.of(publicKey);
/*  57 */       return this;
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
/*     */     public Builder setPrimes(SecretBigInteger p, SecretBigInteger q) {
/*  69 */       this.p = Optional.of(p);
/*  70 */       this.q = Optional.of(q);
/*  71 */       return this;
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
/*     */     public Builder setPrivateExponent(SecretBigInteger d) {
/*  83 */       this.d = Optional.of(d);
/*  84 */       return this;
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
/*     */     public Builder setPrimeExponents(SecretBigInteger dP, SecretBigInteger dQ) {
/*  96 */       this.dP = Optional.of(dP);
/*  97 */       this.dQ = Optional.of(dQ);
/*  98 */       return this;
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
/*     */     public Builder setCrtCoefficient(SecretBigInteger qInv) {
/* 110 */       this.qInv = Optional.of(qInv);
/* 111 */       return this;
/*     */     }
/*     */     
/*     */     @AccessesPartialKey
/*     */     public JwtRsaSsaPssPrivateKey build() throws GeneralSecurityException {
/* 116 */       if (!this.publicKey.isPresent()) {
/* 117 */         throw new GeneralSecurityException("Cannot build without a RSA SSA PSS public key");
/*     */       }
/*     */       
/* 120 */       if (!this.p.isPresent() || !this.q.isPresent()) {
/* 121 */         throw new GeneralSecurityException("Cannot build without prime factors");
/*     */       }
/* 123 */       if (!this.d.isPresent()) {
/* 124 */         throw new GeneralSecurityException("Cannot build without private exponent");
/*     */       }
/* 126 */       if (!this.dP.isPresent() || !this.dQ.isPresent()) {
/* 127 */         throw new GeneralSecurityException("Cannot build without prime exponents");
/*     */       }
/* 129 */       if (!this.qInv.isPresent()) {
/* 130 */         throw new GeneralSecurityException("Cannot build without CRT coefficient");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       RsaSsaPssPrivateKey rsaSsaPssPrivateKey = RsaSsaPssPrivateKey.builder().setPublicKey(((JwtRsaSsaPssPublicKey)this.publicKey.get()).getRsaSsaPssPublicKey()).setPrimes(this.p.get(), this.q.get()).setPrivateExponent(this.d.get()).setPrimeExponents(this.dP.get(), this.dQ.get()).setCrtCoefficient(this.qInv.get()).build();
/* 140 */       return new JwtRsaSsaPssPrivateKey(this.publicKey.get(), rsaSsaPssPrivateKey);
/*     */     }
/*     */     
/*     */     private Builder() {} }
/*     */   
/*     */   private JwtRsaSsaPssPrivateKey(JwtRsaSsaPssPublicKey publicKey, RsaSsaPssPrivateKey rsaSsaPssPrivateKey) {
/* 146 */     this.publicKey = publicKey;
/* 147 */     this.rsaSsaPssPrivateKey = rsaSsaPssPrivateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 156 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPssParameters getParameters() {
/* 162 */     return this.publicKey.getParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPssPublicKey getPublicKey() {
/* 168 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeP() {
/* 179 */     return this.rsaSsaPssPrivateKey.getPrimeP();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeQ() {
/* 190 */     return this.rsaSsaPssPrivateKey.getPrimeQ();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrivateExponent() {
/* 196 */     return this.rsaSsaPssPrivateKey.getPrivateExponent();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeExponentP() {
/* 202 */     return this.rsaSsaPssPrivateKey.getPrimeExponentP();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeExponentQ() {
/* 208 */     return this.rsaSsaPssPrivateKey.getPrimeExponentQ();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getCrtCoefficient() {
/* 214 */     return this.rsaSsaPssPrivateKey.getCrtCoefficient();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 219 */     if (!(o instanceof JwtRsaSsaPssPrivateKey)) {
/* 220 */       return false;
/*     */     }
/* 222 */     JwtRsaSsaPssPrivateKey that = (JwtRsaSsaPssPrivateKey)o;
/* 223 */     return (that.publicKey.equalsKey(this.publicKey) && that.rsaSsaPssPrivateKey
/* 224 */       .equalsKey((Key)this.rsaSsaPssPrivateKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   RsaSsaPssPrivateKey getRsaSsaPssPrivateKey() {
/* 233 */     return this.rsaSsaPssPrivateKey;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPssPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */